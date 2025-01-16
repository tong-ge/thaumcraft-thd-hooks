package bruce.projectreflection.tchooks;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public class ModConfig {
    public static boolean logRegistry=true;
    public static boolean logStacktrace=false;
    public static boolean redirectForRegistry=true;
    public static boolean redirectForRetrieval=true;
    public static boolean logRedirect=false;
    public static boolean logNoAspect=false;

    public static String[] forgeCapsForRemoval={"astralsorcery:cap_item_amulet_holder","distinctdamagedescriptions:dmgdistribution"};
    public static boolean cancelNullRegistries=true;
    public static boolean lockRegistries=true;
    public static boolean crashOnLockedRegistry= (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static boolean allowDynamicRegistration=false;
    public static boolean logDynamicRegistrations=false;
}
