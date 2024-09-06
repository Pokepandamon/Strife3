package net.pokepandamon.strife3.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class CustomItem extends Item {
    public CustomItem(Settings settings) {
        super(settings);
    }

    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        return TypedActionResult.success(user.getStackInHand(hand));
    }*/

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        //super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.strife3.butcher_knife.tooltip"));
    }

    /*@Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.tutorial.custom_item.tooltip"));
    }*/
}
