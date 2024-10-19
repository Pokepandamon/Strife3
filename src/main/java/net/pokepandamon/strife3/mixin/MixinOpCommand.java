package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.dedicated.command.OpCommand;
import net.pokepandamon.strife3.networking.PermissionLevelRequestS2CPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OpCommand.class)
public class MixinOpCommand {
	//@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;addToOperators(Lcom/mojang/authlib/GameProfile;)V"), method = "op")
	//@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;addToOperators(Lcom/mojang/authlib/GameProfile;)V"),  method = "op")
	@WrapOperation(method = "op", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;addToOperators(Lcom/mojang/authlib/GameProfile;)V"))
	private static void op(PlayerManager instance, GameProfile profile, Operation<Void> original) throws CommandSyntaxException {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		//GameProfile gameProfile;
		ServerPlayNetworking.send(instance.getPlayer(profile.getId()), new PermissionLevelRequestS2CPayload(4));
		instance.addToOperators(profile);
		//return profile;
	}
}