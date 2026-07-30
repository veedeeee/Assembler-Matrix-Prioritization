package wtf.vd.assemblermatrix_prioritization.mixin;

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
import wtf.vd.assemblermatrix_prioritization.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePriorityMixin implements IPriorityHost, MatrixPriorityHost {

    @Unique
    private static final String MEPRIORITIZECRAFT_PRIORITY_TAG = "assemblermatrix_prioritization_priority";

    @Unique
    private int assemblermatrix_prioritization$matrixPriority;

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$savePriorityLegacy(CompoundTag data, CallbackInfo ci) {
        assemblermatrix_prioritization$writePriority(data);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$loadPriorityLegacy(CompoundTag data, CallbackInfo ci) {
        assemblermatrix_prioritization$readPriority(data);
    }

    @Override
    public int getPriority() {
        return this.assemblermatrix_prioritization$matrixPriority;
    }

    // ICraftingProvider.getPatternPriority() — not declared in this mixin's implements clause,
    // so @Override is intentionally omitted. Mixin merges this method into TileAssemblerMatrixBase
    // which does implement ICraftingProvider, wiring the priority into AE2's crafting planner.
    public int getPatternPriority() {
        return this.assemblermatrix_prioritization$matrixPriority;
    }

    @Override
    public void setPriority(int priority) {
        this.assemblermatrix_prioritization$setMatrixPriority(priority);
    }

    @Override
    public int assemblermatrix_prioritization$getMatrixPriority() {
        return this.assemblermatrix_prioritization$matrixPriority;
    }

    @Override
    public void assemblermatrix_prioritization$setMatrixPriority(int priority) {
        if (this.assemblermatrix_prioritization$matrixPriority == priority) {
            return;
        }

        this.assemblermatrix_prioritization$matrixPriority = priority;
        assemblermatrix_prioritization$invokeNoArg(this, "saveChanges");
        this.assemblermatrix_prioritization$syncClusterPriority(priority);
    }

    @Override
    public void assemblermatrix_prioritization$setMatrixPriorityFromCluster(int priority) {
        if (this.assemblermatrix_prioritization$matrixPriority == priority) {
            return;
        }

        this.assemblermatrix_prioritization$matrixPriority = priority;
        assemblermatrix_prioritization$invokeNoArg(this, "saveChanges");
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
    private void assemblermatrix_prioritization$syncClusterPriority(int priority) {
        var cluster = assemblermatrix_prioritization$invokeNoArg((Object) this, "getCluster");
        if (cluster == null) {
            return;
        }

        // Propagate to all block entities in the cluster (Frame/Glass/Wall/Pattern/etc.)
        // so that whichever outer block the player right-clicks next shows the correct value.
        var blockEntities = assemblermatrix_prioritization$invokeNoArg(cluster, "getBlockEntities");
        if (blockEntities instanceof java.util.Iterator<?> beIterator) {
            while (beIterator.hasNext()) {
                var be = beIterator.next();
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.assemblermatrix_prioritization$setMatrixPriorityFromCluster(priority);
                }
            }
        } else if (blockEntities instanceof Iterable<?> beIterable) {
            for (var be : beIterable) {
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.assemblermatrix_prioritization$setMatrixPriorityFromCluster(priority);
                }
            }
        }

        // Additionally trigger ICraftingProvider.requestUpdate on Pattern blocks so that
        // AE2's crafting planner re-reads the updated getPatternPriority() value.
        var patterns = assemblermatrix_prioritization$invokeNoArg(cluster, "getPatterns");
        if (!(patterns instanceof Iterable<?> iterable)) {
            return;
        }

        for (var pattern : iterable) {
            if (pattern instanceof ICraftingProvider craftingProvider) {
                assemblermatrix_prioritization$requestUpdate(craftingProvider, pattern);
            }
        }
    }


    @Unique
    private static void assemblermatrix_prioritization$requestUpdate(ICraftingProvider craftingProvider, Object holder) {
        var mainNode = assemblermatrix_prioritization$invokeNoArg(holder, "getMainNode");
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
    private static Object assemblermatrix_prioritization$invokeNoArg(Object target, String methodName) {
        try {
            var method = target.getClass().getMethod(methodName);
            return method.invoke(target);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to invoke method " + methodName + " on " + target.getClass(), e);
        }
    }

    @Unique
    private static void assemblermatrix_prioritization$invokeWriteInt(Object target, String key, int value) {
        try {
            var method = target.getClass().getMethod("putInt", String.class, int.class);
            method.invoke(target, key, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to write priority on " + target.getClass(), e);
        }
    }

    @Unique
    private static int assemblermatrix_prioritization$invokeReadIntOr(Object target, String key, int fallback) {
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
    private void assemblermatrix_prioritization$writePriority(CompoundTag data) {
        assemblermatrix_prioritization$invokeWriteInt(data, MEPRIORITIZECRAFT_PRIORITY_TAG, this.assemblermatrix_prioritization$matrixPriority);
    }

    @Unique
    private void assemblermatrix_prioritization$readPriority(CompoundTag data) {
        this.assemblermatrix_prioritization$matrixPriority = assemblermatrix_prioritization$invokeReadIntOr(data, MEPRIORITIZECRAFT_PRIORITY_TAG, 0);
    }
}
