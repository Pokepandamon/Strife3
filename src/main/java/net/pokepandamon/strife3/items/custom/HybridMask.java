package net.pokepandamon.strife3.items.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.pokepandamon.strife3.items.CustomArmor;
import net.pokepandamon.strife3.items.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.enchantment.Enchantments.RESPIRATION;

public class HybridMask extends CustomArmor implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HybridMask(String itemType, RegistryEntry<ArmorMaterial> armorMaterial, Type type, Settings properties) {
        super(itemType,armorMaterial, type, properties);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player){
        stack.addEnchantment(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(RESPIRATION).get(), 19);
        stack.set(DataComponentTypes.ENCHANTMENTS, stack.getEnchantments().withShowInTooltip(false));
        return false;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer){
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @Nullable <T extends LivingEntity> BipedEntityModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable BipedEntityModel<T> original) {
                if(this.renderer == null)
                    this.renderer = new HybridMaskArmorRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            // Apply our generic idle animation.
            // Whether it plays or not is decided down below.
            state.getController().setAnimation(DefaultAnimations.IDLE);

            LivingEntity entity = (LivingEntity) state.getData(DataTickets.ENTITY);;
            // Let's gather some data from the state to use below
            // This is the entity that is currently wearing/holding the item
            /*if (state.getData(DataTickets.ENTITY) instanceof LivingEntity){
                entity = (LivingEntity) state.getData(DataTickets.ENTITY);
            }*/

            // We'll just have ArmorStands always animate, so we can return here
            if (entity instanceof ArmorStandEntity)
                return PlayState.CONTINUE;

            // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
            // Let's collect the armor pieces the entity is currently wearing
            //Set<GeoRenderEvent.Item> wornArmor = new ObjectOpenHashSet<>();
            Set<Item> wornArmor = new ObjectOpenHashSet<>();
            for (ItemStack stack : entity.getArmorItems()) {
                // We can stop immediately if any of the slots are empty
                if (stack.isEmpty())
                    return PlayState.STOP;

                wornArmor.add(stack.getItem());
            }

            // Check each of the pieces match our set
            boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                    ModItems.DIVERS_MASK,
                    ModItems.KATANA));

            // Play the animation if the full set is being worn, otherwise stop
            return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
