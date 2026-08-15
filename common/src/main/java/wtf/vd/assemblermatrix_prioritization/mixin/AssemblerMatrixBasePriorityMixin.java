package wtf.vd.assemblermatrix_prioritization.mixin;

import appeng.api.networking.crafting.ICraftingProvider;
import appeng.helpers.IPriorityHost;
import appeng.menu.ISubMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import wtf.vd.assemblermatrix_prioritization.Constants;
import wtf.vd.assemblermatrix_prioritization.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePriorityMixin implements IPriorityHost, MatrixPriorityHost {

    @Unique
    private int assemblermatrix_prioritization$matrixPriority;

    @Override
    public int getPriority() {
        return this.assemblermatrix_prioritization$matrixPriority;
    }

    // ICraftingProvider.getPatternPriority() — not declared in this mixin's implements clause,
    // so @Override is intentionally omitted. Mixin merges this method into TileAssemblerMatrixBase.
    // TileAssemblerMatrixBase itself does NOT implement ICraftingProvider; only the
    // TileAssemblerMatrixPattern subclass does. Because Java resolves inherited concrete methods
    // from the superclass ahead of interface defaults, this method still ends up satisfying
    // ICraftingProvider.getPatternPriority() for TileAssemblerMatrixPattern instances.
    public int getPatternPriority() {
        int value = this.assemblermatrix_prioritization$matrixPriority;
        Constants.LOG.info("[DEBUG-priority] getPatternPriority() called on {} @ {} -> {}",
                this.getClass().getName(), assemblermatrix_prioritization$posOf(this), value);
        return value;
    }

    @Override
    public void setPriority(int priority) {
        Constants.LOG.info("[DEBUG-priority] IPriorityHost.setPriority({}) called on {} @ {}",
                priority, this.getClass().getName(), assemblermatrix_prioritization$posOf(this));
        this.assemblermatrix_prioritization$setMatrixPriority(priority);
    }

    @Override
    public int assemblermatrix_prioritization$getMatrixPriority() {
        return this.assemblermatrix_prioritization$matrixPriority;
    }

    @Override
    public void assemblermatrix_prioritization$setMatrixPriority(int priority) {
        Constants.LOG.info("[DEBUG-priority] setMatrixPriority({}) on {} @ {}, current={}",
                priority, this.getClass().getName(), assemblermatrix_prioritization$posOf(this),
                this.assemblermatrix_prioritization$matrixPriority);
        if (this.assemblermatrix_prioritization$matrixPriority == priority) {
            Constants.LOG.info("[DEBUG-priority] setMatrixPriority: no-op (value unchanged)");
            return;
        }

        this.assemblermatrix_prioritization$matrixPriority = priority;
        assemblermatrix_prioritization$invokeNoArg(this, "saveChanges");
        this.assemblermatrix_prioritization$syncClusterPriority(priority);
    }

    @Override
    public void assemblermatrix_prioritization$setMatrixPriorityFromCluster(int priority) {
        Constants.LOG.info("[DEBUG-priority] setMatrixPriorityFromCluster({}) on {} @ {}, current={}",
                priority, this.getClass().getName(), assemblermatrix_prioritization$posOf(this),
                this.assemblermatrix_prioritization$matrixPriority);
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
        Constants.LOG.info("[DEBUG-priority] syncClusterPriority({}) from {} @ {} -> cluster={}",
                priority, this.getClass().getName(), assemblermatrix_prioritization$posOf(this),
                cluster == null ? "null" : cluster.getClass().getName());
        if (cluster == null) {
            Constants.LOG.info("[DEBUG-priority] syncClusterPriority: aborting, cluster is null (multiblock not formed?)");
            return;
        }

        // Propagate to all block entities in the cluster (Frame/Glass/Wall/Pattern/etc.)
        // so that whichever outer block the player right-clicks next shows the correct value.
        var blockEntities = assemblermatrix_prioritization$invokeNoArg(cluster, "getBlockEntities");
        int propagatedCount = 0;
        if (blockEntities instanceof java.util.Iterator<?> beIterator) {
            while (beIterator.hasNext()) {
                var be = beIterator.next();
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.assemblermatrix_prioritization$setMatrixPriorityFromCluster(priority);
                    propagatedCount++;
                }
            }
        } else if (blockEntities instanceof Iterable<?> beIterable) {
            for (var be : beIterable) {
                if (be == (Object) this) {
                    continue; // already updated in setMatrixPriority
                }
                if (be instanceof MatrixPriorityHost host) {
                    host.assemblermatrix_prioritization$setMatrixPriorityFromCluster(priority);
                    propagatedCount++;
                }
            }
        } else {
            Constants.LOG.info("[DEBUG-priority] syncClusterPriority: getBlockEntities() returned unsupported type {}",
                    blockEntities == null ? "null" : blockEntities.getClass().getName());
        }
        Constants.LOG.info("[DEBUG-priority] syncClusterPriority: propagated to {} other block entities", propagatedCount);

        // Additionally trigger ICraftingProvider.requestUpdate on Pattern blocks so that
        // AE2's crafting planner re-reads the updated getPatternPriority() value.
        var patterns = assemblermatrix_prioritization$invokeNoArg(cluster, "getPatterns");
        if (!(patterns instanceof Iterable<?> iterable)) {
            Constants.LOG.info("[DEBUG-priority] syncClusterPriority: getPatterns() returned unsupported type {}",
                    patterns == null ? "null" : patterns.getClass().getName());
            return;
        }

        int patternCount = 0;
        int updatedCount = 0;
        for (var pattern : iterable) {
            patternCount++;
            if (pattern instanceof ICraftingProvider craftingProvider) {
                try {
                    assemblermatrix_prioritization$requestUpdate(craftingProvider, pattern);
                    updatedCount++;
                } catch (RuntimeException e) {
                    // Do not let one failing pattern (e.g. not yet grid-connected) abort the
                    // whole propagation loop for the remaining pattern blocks in the cluster.
                    Constants.LOG.warn("[DEBUG-priority] syncClusterPriority: requestUpdate failed for pattern {} @ {}",
                            pattern.getClass().getName(), assemblermatrix_prioritization$posOf(pattern), e);
                }
            }
        }
        Constants.LOG.info("[DEBUG-priority] syncClusterPriority: getPatterns() returned {} pattern(s), requestUpdate succeeded for {}",
                patternCount, updatedCount);
    }


    @Unique
    private static void assemblermatrix_prioritization$requestUpdate(ICraftingProvider craftingProvider, Object holder) {
        var mainNode = assemblermatrix_prioritization$invokeNoArg(holder, "getMainNode");
        if (mainNode == null) {
            Constants.LOG.info("[DEBUG-priority] requestUpdate: getMainNode() returned null for {} @ {}",
                    holder.getClass().getName(), assemblermatrix_prioritization$posOf(holder));
            return;
        }

        for (var method : ICraftingProvider.class.getMethods()) {
            if (!method.getName().equals("requestUpdate") || method.getParameterCount() != 1) {
                continue;
            }

            try {
                method.invoke(null, mainNode);
                Constants.LOG.info("[DEBUG-priority] requestUpdate: dispatched for {} @ {}",
                        holder.getClass().getName(), assemblermatrix_prioritization$posOf(holder));
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
    private static String assemblermatrix_prioritization$posOf(Object target) {
        if (target instanceof BlockEntity be) {
            return String.valueOf(be.getBlockPos());
        }
        return "unknown-pos";
    }

}
