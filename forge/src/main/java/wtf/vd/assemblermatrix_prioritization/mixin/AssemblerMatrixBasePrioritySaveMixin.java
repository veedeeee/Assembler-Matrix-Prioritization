package wtf.vd.assemblermatrix_prioritization.mixin;

import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.assemblermatrix_prioritization.Constants;
import wtf.vd.assemblermatrix_prioritization.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePrioritySaveMixin {

    private static final String PRIORITY_TAG = "assemblermatrix_prioritization_priority";

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$savePriority(CompoundTag data, CallbackInfo ci) {
        int value = ((MatrixPriorityHost) this).assemblermatrix_prioritization$getMatrixPriority();
        Constants.LOG.info("[DEBUG-priority] (forge) savePriority: writing {} for {}", value, this.getClass().getName());
        data.putInt(PRIORITY_TAG, value);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$loadPriority(CompoundTag data, CallbackInfo ci) {
        int value = data.contains(PRIORITY_TAG) ? data.getInt(PRIORITY_TAG) : 0;
        Constants.LOG.info("[DEBUG-priority] (forge) loadPriority: read {} for {}", value, this.getClass().getName());
        ((MatrixPriorityHost) this).assemblermatrix_prioritization$setMatrixPriorityFromCluster(value);
    }
}
