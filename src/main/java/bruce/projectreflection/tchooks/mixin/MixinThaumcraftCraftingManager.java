package bruce.projectreflection.tchooks.mixin;

import bruce.projectreflection.tchooks.ModConfig;
import bruce.projectreflection.tchooks.TCHooks;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

@Mixin(value = ThaumcraftCraftingManager.class,remap = false)
public class MixinThaumcraftCraftingManager {
    @Redirect(method = "generateTags(Lnet/minecraft/item/ItemStack;Ljava/util/ArrayList;)Lthaumcraft/api/aspects/AspectList;",at = @At(value = "INVOKE",target = "Lthaumcraft/api/ThaumcraftApi;registerObjectTag(Lnet/minecraft/item/ItemStack;Lthaumcraft/api/aspects/AspectList;)V"))
    private static void onRegisterObjectTag(ItemStack item, AspectList aspects)
    {
        if(ModConfig.allowDynamicRegistration)
        {
            if(ModConfig.logDynamicRegistrations)
            {
                TCHooks.LOGGER.warn("Dynamically registering aspects!");
            }
            (new AspectEventProxy()).registerObjectTag(item, aspects);
        }
        else if(ModConfig.logDynamicRegistrations){
            TCHooks.LOGGER.info("Cancelling dynamic registration:{}",item.serializeNBT().toString());
        }
    }
}
