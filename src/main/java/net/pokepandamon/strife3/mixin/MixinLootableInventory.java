package net.pokepandamon.strife3.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.LootableInventory;
import net.pokepandamon.strife3.Strife3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LootableInventory.class)
public interface MixinLootableInventory extends Inventory {
	/*@Inject(method = "readLootTable",at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getString(Ljava/lang/String;)Ljava/lang/String;"), cancellable = false)
	private void newLootTables(NbtCompound nbt, CallbackInfoReturnable<String> cir){
		Strife3.LOGGER.info(nbt.getString("LootTable"));
		cir.setReturnValue(nbt.getString("LootTable"));
	}*/
	@ModifyArg(method = "readLootTable", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;of(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
	private String newLootTables(String id){
		//Strife3.LOGGER.info(id.replace("strife_season_3_loot_tables:","strife3:chest/"));
		return id.replace("strife_season_3_loot_tables:","strife3:chest/");
	}
}