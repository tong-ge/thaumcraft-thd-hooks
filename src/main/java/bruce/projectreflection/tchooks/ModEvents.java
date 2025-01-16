package bruce.projectreflection.tchooks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.AspectRegistryEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class ModEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAspectRegistry(AspectRegistryEvent event)
    {
        if(ModConfig.lockRegistries)
        {
            TCHooks.LOGGER.info("Locking registries!");
            TCHooks.registryLocked=true;
        }
    }
}
