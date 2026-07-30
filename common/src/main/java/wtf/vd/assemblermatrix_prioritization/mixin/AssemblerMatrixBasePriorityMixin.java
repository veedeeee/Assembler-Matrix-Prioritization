package wtf.vd.meprioritizecraft.mixin;

import appeng.api.networking.crafting.ICraftingProvider;
import appeng.helpers.IPriorityHost;
import appeng.menu.ISubMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.meprioritizecraft.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePriorityMixin implements IPriorityHost, MatrixPriorityHost {

    @Unique
    private static final String MEPRIORITIZECRAFT_PRIORITY_TAG = "meprioritizecraft_priority";

    @Unique
    private int meprioritizecraft$matrixPriority;

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$savePriorityLegacy(CompoundTag data, CallbackInfo ci) {
        meprioritizecraft$writePriority(data);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$loadPriorityLegacy(CompoundTag data, CallbackInfo ci) {
        meprioritizecraft$readPriority(data);
    }

    @Override
    public int getPriority() {
        return this.meprioritizecraft$matrixPriority;
    }

    // ICraftingProvider.getPatternPriority() — not declared in this mixin's implements clause,
    // so @Override is intentionally omitted. Mixin merges this method into TileAssemblerMatrixBase
    // which does implement ICraftingProvider, wiring the priority into AE2's crafting planner.
    public int getPatternPriority() {
        return this.meprioritizecraft$matrixPriority;
    }

    @Override
    public void setPriority(int priority) {
        this.meprioritizecraft$setMatrixPriority(priority);
    }

    @Override
    public int meprioritizecraft$getMatrixPriority() {
        return this.meprioritizecraft$matrixPriority;
    }

    @Override
    public void meprioritizecraft$setMatrixPriority(int priority) {
        if (this.meprioritizecraft$matrixPriority == priority) {
            return;
        }

        this.meprioritizecraft$matrixPriority = priority;
        meprioritizecraft$invokeNoArg(this, "saveChanges");
        this.meprioritizecraft$syncClusterPriority(priority);
    }

    @Override
    public void meprioritizecraft$setMatrixPriorityFromCluster(int priority) {
        if (this.meprioritizecraft$matrixPriority == priority) {
            return;
        }

        this.meprioritizecraft$matrixPriority = priority;
        meprioritizecraft$invokeNoArg(this, "saveChanges");
    }

    @Override
    public void returnToMainMenu(Player player, ISubMenu subMenu) {
        try {
            var containerClass = Class.forName("com.glodblock.github.extendedae.container.ContainerAssemblerMatrix");
            var menuType = containerClass.getField("TYPE").get(null);
            var menuOpenerClass = Class.forName("appeng.menu.MenuOpener");
            var locator = subMenu.getLocator();

            // AE2 uses different locator types across versions (MenuLocator in 1.20.1, MenuHostLocator in 1.21+).
            // Try the locator's concrete type and its supertypes first, then fall back to any 3-arg returnTo.
            java.lang.reflect.Method returnToMethod = null;
            for (var locatorType : new Class<?>[]{ locator.getClass(), locator.getClass().getSuperclass() }) {
                if (locatorType == null) continue;
                try {
                    returnToMethod = menuOpenerClass.getMethod("returnTo",
                            net.minecraft.world.inventory.MenuType.class,
                            Player.class,
                            locatorType);
                    break;
                } catch (NoSuchMethodException ignored) {}
            }
            if (returnToMethod == null) {
                for (var method : menuOpenerClass.getMethods()) {
                    if (method.getName().equals("returnTo") && method.getParameterCount() == 3) {
                        returnToMethod = method;
                        break;
                    }
                }
            }
            if (returnToMethod == null) {
                throw new IllegalStateException("returnTo method not found on MenuOpener");
            }
            returnToMethod.invoke(null, menuType, player, locator);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to return to assembler matrix menu", e);
        }
    }

    @Override
    public ItemStack getMainMenuIcon() {
        var self = (BlockEntity) (Object) this;
        return new ItemStack(self.getBlockState().getBlock());
    }

    @Unique
    private void meprioritizecraft$syncClusterPriority(int priority) {
        var cluster = meprioritizecraft$invokeNoArg((Object) this, "getCluster");
        if (cluster == null) {
            return;
        }

        // Propagate to all block entities in the cluster (Frame/Glass/Wall/Pattern/etc.)
        // so that whichever outer block the player right-clicks next shows the correct value.
        var blockEntities = meprioritizecraft$invokeNoArg(cluster, "getBlockEntities");
        if (blockEntities instanceof java.util.Iterator<?> beIterator) {
            while (beIterator.hasNext()) {
                var be = beIterator.next();
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.meprioritizecraft$setMatrixPriorityFromCluster(priority);
                }
            }
        } else if (blockEntities instanceof Iterable<?> beIterable) {
            for (var be : beIterable) {
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.meprioritizecraft$setMatrixPriorityFromCluster(priority);
                }
            }
        }

        // Additionally trigger ICraftingProvider.requestUpdate on Pattern blocks so that
        // AE2's crafting planner re-reads the updated getPatternPriority() value.
        var patterns = meprioritizecraft$invokeNoArg(cluster, "getPatterns");
        if (!(patterns instanceof Iterable<?> iterable)) {
            return;
        }

        for (var pattern : iterable) {
            if (pattern instanceof ICraftingProvider craftingProvider) {
                meprioritizecraft$requestUpdate(craftingProvider, pattern);
            }
        }
    }


    @Unique
    private static void meprioritizecraft$requestUpdate(ICraftingProvider craftingProvider, Object holder) {
        var mainNode = meprioritizecraft$invokeNoArg(holder, "getMainNode");
        if (mainNode == null) {
            return;
        }

        for (var method : ICraftingProvider.class.getMethods()) {
            if (!method.getName().equals("requestUpdate") || method.getParameterCount() != 1) {
                continue;
            }

            try {
                method.invoke(null, mainNode);
                return;
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to request crafting update for " + holder.getClass(), e);
            }
        }

        throw new IllegalStateException("No requestUpdate(node) method found on ICraftingProvider");
    }
    @Unique
    private static Object meprioritizecraft$invokeNoArg(Object target, String methodName) {
        try {
            var method = target.getClass().getMethod(methodName);
            return method.invoke(target);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to invoke method " + methodName + " on " + target.getClass(), e);
        }
    }

    @Unique
    private static void meprioritizecraft$invokeWriteInt(Object target, String key, int value) {
        try {
            var method = target.getClass().getMethod("putInt", String.class, int.class);
            method.invoke(target, key, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to write priority on " + target.getClass(), e);
        }
    }

    @Unique
    private static int meprioritizecraft$invokeReadIntOr(Object target, String key, int fallback) {
        try {
            var method = target.getClass().getMethod("getIntOr", String.class, int.class);
            return (int) method.invoke(target, key, fallback);
        } catch (NoSuchMethodException ignored) {
            try {
                var method = target.getClass().getMethod("getInt", String.class);
                return (int) method.invoke(target, key);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to read priority on " + target.getClass(), e);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to read priority on " + target.getClass(), e);
        }
    }

    @Unique
    private void meprioritizecraft$writePriority(CompoundTag data) {
        meprioritizecraft$invokeWriteInt(data, MEPRIORITIZECRAFT_PRIORITY_TAG, this.meprioritizecraft$matrixPriority);
    }

    @Unique
    private void meprioritizecraft$readPriority(CompoundTag data) {
        this.meprioritizecraft$matrixPriority = meprioritizecraft$invokeReadIntOr(data, MEPRIORITIZECRAFT_PRIORITY_TAG, 0);
    }
}
