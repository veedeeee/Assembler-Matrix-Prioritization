package wtf.vd.assemblermatrix_prioritization.mixin;

import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.assemblermatrix_prioritization.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePrioritySaveMixin {

    private static final String PRIORITY_TAG = "assemblermatrix_prioritization_priority";

    // Try both the official name and the SRG (searge) name. ExtendedAE's published Forge
    // 1.20.1 jar overrides vanilla BlockEntity#saveAdditional, and depending on how/when it was
    // built, the override may still carry its SRG name (m_183515_) in the distributed bytecode
    // instead of being deobfuscated to "saveAdditional". Since this is a @Pseudo mixin with
    // remap=false, only a literal name match works, so both candidates are listed with require=0.
    @Inject(method = {
            "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V",
            "m_183515_(Lnet/minecraft/nbt/CompoundTag;)V"
    }, at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$savePriority(CompoundTag data, CallbackInfo ci) {
        int value = ((MatrixPriorityHost) this).assemblermatrix_prioritization$getMatrixPriority();
        data.putInt(PRIORITY_TAG, value);
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$loadPriority(CompoundTag data, CallbackInfo ci) {
        int value = data.contains(PRIORITY_TAG) ? data.getInt(PRIORITY_TAG) : 0;
        ((MatrixPriorityHost) this).assemblermatrix_prioritization$setMatrixPriorityFromCluster(value);
    }
}
