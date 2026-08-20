package wtf.vd.assemblermatrix_prioritization.mixin;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.assemblermatrix_prioritization.access.MatrixPriorityHost;

// NeoForge 26.1.2 replaced the CompoundTag-based BlockEntity.saveAdditional/loadTag signatures
// with the ValueOutput/ValueInput codec API. This module-specific mixin handles persistence for
// ExtendedAE on that line; the neoforge (1.21.1) module keeps the CompoundTag-based variant.
@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePrioritySaveMixin {

    private static final String PRIORITY_TAG = "assemblermatrix_prioritization_priority";

    @Inject(method = "saveAdditional(Lnet/minecraft/world/level/storage/ValueOutput;)V",
            at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$savePriority(ValueOutput output, CallbackInfo ci) {
        int value = ((MatrixPriorityHost) this).assemblermatrix_prioritization$getMatrixPriority();
        output.putInt(PRIORITY_TAG, value);
    }

    @Inject(method = "loadTag(Lnet/minecraft/world/level/storage/ValueInput;)V",
            at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$loadPriority(ValueInput input, CallbackInfo ci) {
        int value = input.getIntOr(PRIORITY_TAG, 0);
        ((MatrixPriorityHost) this).assemblermatrix_prioritization$setMatrixPriorityFromCluster(value);
    }
}
