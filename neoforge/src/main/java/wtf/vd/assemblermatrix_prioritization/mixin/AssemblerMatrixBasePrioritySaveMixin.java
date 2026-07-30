package wtf.vd.meprioritizecraft.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.meprioritizecraft.access.MatrixPriorityHost;

// MC 1.21.1 changed BlockEntity.saveAdditional / loadTag to 2-param signatures.
// This NeoForge-specific mixin handles persistence for ExtendedAE on NeoForge 1.21.1.
// The common module retains the 1-param variant for Forge 1.20.1 compatibility.
@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixBase", remap = false)
public abstract class AssemblerMatrixBasePrioritySaveMixin {

    @Inject(method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V",
            at = @At("TAIL"), require = 0)
    private void meprioritizecraft$savePriority(CompoundTag data, HolderLookup.Provider registries, CallbackInfo ci) {
        data.putInt("meprioritizecraft_priority", ((MatrixPriorityHost) this).meprioritizecraft$getMatrixPriority());
    }

    @Inject(method = "loadTag(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V",
            at = @At("TAIL"), require = 0)
    private void meprioritizecraft$loadPriority(CompoundTag data, HolderLookup.Provider registries, CallbackInfo ci) {
        ((MatrixPriorityHost) this).meprioritizecraft$setMatrixPriorityFromCluster(
                data.getInt("meprioritizecraft_priority"));
    }
}
