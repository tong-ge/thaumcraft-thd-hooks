package bruce.projectreflection.tchooks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.aspects.AspectList;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,
dependencies = "required-after:mixinbooter;required-before:thaumcraft;")
public class TCHooks implements ILateMixinLoader{

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    public static boolean registryLocked=false;

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //todo pre-initialization
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.tchooks.json");
    }
    public static void logRegisterTag(ItemStack stack, AspectList aspects,Throwable throwable)
    {
        if (aspects == null) {
            if(!ModConfig.logNoAspect)
            {
                return;
            }
            aspects = new AspectList();
        }
        if(ModConfig.logNoAspect || aspects.size() >0) {
            AspectList finalAspects = aspects;
            LOGGER.info("Registering aspects {} for item {}",
                    Arrays.stream(aspects.getAspects())
                            .map(aspect -> Optional.ofNullable(aspect)
                                    .map(aspect1 -> aspect1.getName() + "*" + finalAspects.getAmount(aspect1))
                                    .orElse("noaspect")
                            )
                            .collect(Collectors.joining(",", "[", "]")),
                    stack.serializeNBT().toString());
            if (throwable != null)
                LOGGER.info("Stacktrace:", throwable);
        }
    }
    public static void modifyNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("tag") && nbt.getTag("tag").isEmpty())
        {
            nbt.removeTag("tag");
        }
        if (nbt.hasKey("ForgeCaps")) {
            NBTTagCompound forgeCaps=nbt.getCompoundTag("ForgeCaps");
            for(String cap:ModConfig.forgeCapsForRemoval)
            {
                if(forgeCaps.hasKey(cap))
                    forgeCaps.removeTag(cap);
            }
            if(forgeCaps.isEmpty())
            {
                nbt.removeTag("ForgeCaps");
            }else {
                nbt.setTag("ForgeCaps",forgeCaps);
            }
        }
    }
}
