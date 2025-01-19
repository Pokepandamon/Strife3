package net.pokepandamon.strife3.mixin;

import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.server.MinecraftServer;
import net.pokepandamon.strife3.WorldCreatorMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldCreator.class)
public class MixinWorldCreator implements WorldCreatorMixinInterface {
	@Unique Boolean strife3World = true;

	public Boolean getStrife3World(){
		return strife3World;
	}

	public void setStrife3World(Boolean value){
		strife3World = value;
	}
}