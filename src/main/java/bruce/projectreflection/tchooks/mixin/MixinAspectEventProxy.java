package bruce.projectreflection.tchooks.mixin;

import bruce.projectreflection.tchooks.ModConfig;
import bruce.projectreflection.tchooks.TCHooks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

@Mixin(value = AspectEventProxy.class,remap = false)
public class MixinAspectEventProxy {
    @Inject(method = "registerObjectTag(Lnet/minecraft/item/ItemStack;Lthaumcraft/api/aspects/AspectList;)V",at=@At("HEAD"),cancellable = true)
    private void pre_registerObjectTag(ItemStack item, AspectList aspects, CallbackInfo ci){
        if(ModConfig.cancelNullRegistries && aspects == null)
        {
            if(ModConfig.logRegistry)
                TCHooks.LOGGER.info("Cancelling registration for {}",item.serializeNBT().toString());
            ci.cancel();
            return;
        }
        if(TCHooks.registryLocked)
        {
            if(ModConfig.crashOnLockedRegistry) {
                RuntimeException e=new IllegalArgumentException("Registry already locked while registering " + item.serializeNBT());
                TCHooks.LOGGER.error("Throwing exeption because registry is locked!",e);
                throw e;
            }
            TCHooks.LOGGER.info("Cancelling registration for {} because registry is locked",item.serializeNBT().toString());
            ci.cancel();
            return;
        }
        if(ModConfig.logRegistry)
            TCHooks.logRegisterTag(item,aspects, ModConfig.logStacktrace?new Throwable():null);
    }
    @Redirect(method = "registerObjectTag(Lnet/minecraft/item/ItemStack;Lthaumcraft/api/aspects/AspectList;)V",at=@At(value = "INVOKE",target = "Lthaumcraft/api/internal/CommonInternals;generateUniqueItemstackId(Lnet/minecraft/item/ItemStack;)I"))
    private int generateUniqueItemstackIdStripForgeCaps(ItemStack stack)
    {
        ItemStack sc = stack.copy();
        sc.setCount(1);
        NBTTagCompound nbt= sc.serializeNBT();
        String original= nbt.toString();
        if(ModConfig.redirectForRegistry) {
            TCHooks.modifyNBT(nbt);
            String modified = nbt.toString();
            if (ModConfig.logRedirect && !modified.equals(original)) {
                TCHooks.LOGGER.info("Stripping forge caps for register:{} -> {}", original, modified);
            }
            return modified.hashCode();
        }
        return original.hashCode();
    }
}
