package net.pokepandamon.strife3.items.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.CustomItem;
import net.pokepandamon.strife3.music.SoundEffects;

public class EtherTablet extends CustomItem {
    private final ViewerCountManager stateManager = new ViewerCountManager() {
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            //world.addSyncedBlockEvent(EnderChestBlockEntity.this.pos, Blocks.ENDER_CHEST, 1, newViewerCount);
        }

        protected boolean isPlayerViewing(PlayerEntity player) {
            return true;
            //return player.getEnderChestInventory().isActiveBlockEntity(EnderChestBlockEntity.this);
        }
    };

    public EtherTablet(String itemType, Settings settings) {
        super(itemType, settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        /*if(user.getItemCooldownManager().isCoolingDown(this)){
            int cooldownSeconds = (int) (user.getItemCooldownManager().getCooldownProgress(this, 1F) * 18000 / 20);
            String cooldownString = null;
            if(cooldownSeconds / 60 < 10){
                cooldownString = "0" + cooldownSeconds / 60;
            }
            cooldownString = cooldownString + cooldownSeconds / 60;
            if(cooldownSeconds % 60 < 10){
                cooldownString = cooldownString + ":0";
            }else{
                cooldownString = cooldownString + ":";
            }
            cooldownString = cooldownString + cooldownSeconds % 60;
            user.sendMessage(Text.literal("Your connection to the ether realm remains unstable, try again in: " + cooldownString).formatted(Formatting.DARK_RED));
            return TypedActionResult.fail(itemStack);
        }*/
        if(!user.getItemCooldownManager().isCoolingDown(this) && !(user.currentScreenHandler instanceof GenericContainerScreenHandler) && !user.getWorld().isClient()) {
            NamedScreenHandlerFactory screenHandlerFactory = new SimpleNamedScreenHandlerFactory(
                    (syncId, inv, p) -> GenericContainerScreenHandler.createGeneric9x3(syncId, inv, user.getEnderChestInventory()),
                    Text.translatable("container.enderchest")
            );
            user.openHandledScreen(screenHandlerFactory);
            user.getItemCooldownManager().set(this, 18000);
            user.getWorld().playSound(user, user.getBlockPos(), SoundEffects.ETHER_TABLET, SoundCategory.PLAYERS, 1F, 1F);
            user.sendMessage(Text.literal("You have been patched to the Ether Realm").formatted(Formatting.DARK_RED).formatted(Formatting.BOLD).formatted(Formatting.ITALIC));
            return TypedActionResult.pass(itemStack);
        }else{
            return TypedActionResult.fail(itemStack);
        }
    }
}
