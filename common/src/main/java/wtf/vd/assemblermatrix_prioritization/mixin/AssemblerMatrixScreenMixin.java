package wtf.vd.assemblermatrix_prioritization.mixin;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.style.WidgetStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.client.gui.GuiAssemblerMatrix", remap = false)
public abstract class AssemblerMatrixScreenMixin {

    @Inject(method = "<init>", at = @At("TAIL"), require = 0)
    private void assemblermatrix_prioritization$addPriorityButton(CallbackInfo ci) {
        var style = ((AEBaseScreen<?>) (Object) this).getStyle();
        var widgetMap = ((ScreenStyleAccessor) style).assemblermatrix_prioritization$getWidgetMap();
        if (!widgetMap.containsKey("openPriority")) {
            // ExtendedAE's assembler_matrix.json does not define the openPriority widget.
            // Inject the widget position here so addOpenPriorityButton() can resolve it.
            var ws = new WidgetStyle();
            ws.setLeft(171);
            ws.setTop(-5);
            ws.setWidth(20);
            ws.setHeight(20);
            widgetMap.put("openPriority", ws);
        }
        ((AEBaseScreenAccessor) this).assemblermatrix_prioritization$getWidgets().addOpenPriorityButton();
    }
}

