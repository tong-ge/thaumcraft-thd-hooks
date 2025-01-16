package bruce.projectreflection.tchooks.mixin;

import bruce.projectreflection.tchooks.ModConfig;
import bruce.projectreflection.tchooks.TCHooks;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.api.internal.CommonInternals;

@Mixin(value = CommonInternals.class,remap = false)
public class MixinCommonInternals {
    @Redirect(method = "generateUniqueItemstackIdStripped",at = @At(value = "INVOKE",target = "Lnet/minecraft/nbt/NBTTagCompound;toString()Ljava/lang/String;"))
    private static String redirect_toString(NBTTagCompound nbt)
    {
        String original= nbt.toString();
        if(ModConfig.redirectForRetrieval) {
            TCHooks.modifyNBT(nbt);
            String modified = nbt.toString();
            if (ModConfig.logRedirect && !modified.equals(original)) {
                TCHooks.LOGGER.info("Stripping forge caps for reading:{} -> {}", original, modified);
            }
            return modified;
        }
        return original;
    }
}
