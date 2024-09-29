package net.pokepandamon.strife3.items.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.effects.MorphineEffect;
import net.pokepandamon.strife3.items.CustomItem;

public class Morphine extends CustomItem {
    private static final StatusEffectInstance morphine = new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.MORPHINE), 15*20+1, 0);

    public Morphine(String itemType, Settings settings){
        super(itemType, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!user.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.MORPHINE))) {
            user.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Strife3.MORPHINE), 15 * 20 + 1, 0));
            user.setCurrentHand(hand);
            //return TypedActionResult.consume(itemStack);
            return TypedActionResult.pass(ItemStack.EMPTY);
        }else{
            if (user instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
                serverPlayer.sendMessage(Text.of("You just used it, it would be irresponsible to do that again"));
            }
            return TypedActionResult.pass(itemStack);
        }
    }
}
