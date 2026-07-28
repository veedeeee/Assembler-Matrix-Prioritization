package wtf.vd.meprioritizecraft.mixin;

import appeng.client.gui.implementations.PriorityScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.helpers.patternprovider.PatternProviderLogic;
import appeng.menu.implementations.PriorityMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.vd.meprioritizecraft.access.MatrixPriorityHost;

@Mixin(value = PriorityScreen.class, remap = false)
public abstract class PriorityScreenMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void meprioritizecraft$setCraftingPriorityTitle(PriorityMenu menu, Inventory inventory, Component title,
            ScreenStyle style, CallbackInfo ci) {
        if (menu.getHost() instanceof PatternProviderLogic || menu.getHost() instanceof MatrixPriorityHost) {
            ((ScreenAccessor) this).meprioritizecraft$setTitle(
                    Component.translatable("gui.meprioritizecraft.crafting_priority"));
        }
    }
}
