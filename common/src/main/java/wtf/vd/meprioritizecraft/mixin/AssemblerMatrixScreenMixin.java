package wtf.vd.meprioritizecraft.mixin;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.style.WidgetStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.meprioritizecraft.access.MatrixPriorityHost;

@Pseudo
@Mixin(targets = "com.glodblock.github.extendedae.client.gui.GuiAssemblerMatrix", remap = false)
public abstract class AssemblerMatrixScreenMixin {

    @Inject(method = "<init>", at = @At("TAIL"), require = 0)
    private void meprioritizecraft$addPriorityButton(CallbackInfo ci) {
        try {
            var menu = ((AEBaseScreen<?>) (Object) this).getMenu();
            var host = menu.getClass().getMethod("getHost").invoke(menu);
            // Only inject the priority button on the core block of the multiblock.
            // Frame/Glass/Wall blocks all open the same GUI but should not each show
            // an independent priority button with its own value.
            if (!(host instanceof MatrixPriorityHost mpHost) || !mpHost.meprioritizecraft$isCore()) {
                return;
            }
        } catch (ReflectiveOperationException e) {
            return;
        }
        var style = ((AEBaseScreen<?>) (Object) this).getStyle();
        var widgetMap = ((ScreenStyleAccessor) style).meprioritizecraft$getWidgetMap();
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
        ((AEBaseScreenAccessor) this).meprioritizecraft$getWidgets().addOpenPriorityButton();
    }
}

