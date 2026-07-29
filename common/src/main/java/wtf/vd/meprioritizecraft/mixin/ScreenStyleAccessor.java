package wtf.vd.meprioritizecraft.mixin;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.style.WidgetStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ScreenStyle.class, remap = false)
public interface ScreenStyleAccessor {

    @Accessor("widgets")
    Map<String, WidgetStyle> meprioritizecraft$getWidgetMap();
}
