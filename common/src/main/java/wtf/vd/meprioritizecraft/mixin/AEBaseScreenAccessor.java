package wtf.vd.meprioritizecraft.mixin;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.WidgetContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AEBaseScreen.class, remap = false)
public interface AEBaseScreenAccessor {

    @Accessor("widgets")
    WidgetContainer meprioritizecraft$getWidgets();
}
