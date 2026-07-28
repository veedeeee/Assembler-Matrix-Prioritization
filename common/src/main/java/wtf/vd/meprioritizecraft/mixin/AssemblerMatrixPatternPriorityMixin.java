package wtf.vd.meprioritizecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import wtf.vd.meprioritizecraft.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.common.tileentities.matrix.TileAssemblerMatrixPattern", remap = false)
public abstract class AssemblerMatrixPatternPriorityMixin {

    public int getPatternPriority() {
        return ((MatrixPriorityHost) this).meprioritizecraft$getMatrixPriority();
    }
}
