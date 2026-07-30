package wtf.vd.meprioritizecraft;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class AeImprovedCraftingPrioritizationNeoForge {

    public AeImprovedCraftingPrioritizationNeoForge(IEventBus ignoredEventBus) {
        CommonClass.init();
    }
}
