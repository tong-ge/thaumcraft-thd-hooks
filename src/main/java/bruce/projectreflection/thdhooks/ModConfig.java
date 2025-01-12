package bruce.projectreflection.thdhooks;

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
}
