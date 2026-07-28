package wtf.vd.meprioritizecraft.mixin;

import appeng.api.networking.crafting.ICraftingProvider;
import appeng.blockentity.grid.AENetworkedBlockEntity;
import appeng.helpers.IPriorityHost;
import appeng.menu.ISubMenu;
import net.minecraft.core.HolderLookup;
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

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$savePriorityWithLookup(CompoundTag data, HolderLookup.Provider registries, CallbackInfo ci) {
        meprioritizecraft$writePriority(data);
    }

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$savePriorityLegacy(CompoundTag data, CallbackInfo ci) {
        meprioritizecraft$writePriority(data);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$loadPriorityWithLookup(CompoundTag data, HolderLookup.Provider registries, CallbackInfo ci) {
        meprioritizecraft$readPriority(data);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$loadPriorityLegacy(CompoundTag data, CallbackInfo ci) {
        meprioritizecraft$readPriority(data);
    }

    @Override
    public int getPriority() {
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
        ((AENetworkedBlockEntity) (Object) this).saveChanges();
        this.meprioritizecraft$syncClusterPriority(priority);
    }

    @Override
    public void meprioritizecraft$setMatrixPriorityFromCluster(int priority) {
        if (this.meprioritizecraft$matrixPriority == priority) {
            return;
        }

        this.meprioritizecraft$matrixPriority = priority;
        ((AENetworkedBlockEntity) (Object) this).saveChanges();
    }

    @Override
    public void returnToMainMenu(Player player, ISubMenu subMenu) {
        try {
            var method = Player.class.getDeclaredMethod("closeContainer");
            method.setAccessible(true);
            method.invoke(player);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to close menu for matrix priority host", e);
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

        var patterns = meprioritizecraft$invokeNoArg(cluster, "getPatterns");
        if (!(patterns instanceof Iterable<?> iterable)) {
            return;
        }

        for (var pattern : iterable) {
            if (pattern instanceof MatrixPriorityHost host) {
                host.meprioritizecraft$setMatrixPriorityFromCluster(priority);
            }
            if (pattern instanceof ICraftingProvider && pattern instanceof AENetworkedBlockEntity networkedBlockEntity) {
                ICraftingProvider.requestUpdate(networkedBlockEntity.getMainNode());
            }
        }
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
