package wtf.vd.meprioritizecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.client.gui.GuiAssemblerMatrix", remap = false)
public abstract class AssemblerMatrixScreenMixin {

    @Inject(method = "<init>", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$addPriorityButton(CallbackInfo ci) {
        ((AEBaseScreenAccessor) this).meprioritizecraft$getWidgets().addOpenPriorityButton();
    }
}
