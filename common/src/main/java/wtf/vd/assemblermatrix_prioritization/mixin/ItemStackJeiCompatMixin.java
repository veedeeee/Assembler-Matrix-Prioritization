package wtf.vd.assemblermatrix_prioritization.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackJeiCompatMixin {

    /**
     * Compatibility bridge for older JEI builds that still call ItemStack#supportsEnchantment(Holder).
     * This path still relies on Enchantment#canEnchant(ItemStack) for backward compatibility.
     */
    @SuppressWarnings("deprecation")
    public boolean supportsEnchantment(Holder<Enchantment> enchantment) {
        return enchantment.value().canEnchant((ItemStack) (Object) this);
    }
}
