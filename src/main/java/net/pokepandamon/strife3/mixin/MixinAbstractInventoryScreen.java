package net.pokepandamon.strife3.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.pokepandamon.strife3.PlayerMixinInterface;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.items.ModItems;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractInventoryScreen.class)
public abstract class MixinAbstractInventoryScreen<T extends ScreenHandler> extends HandledScreen<T> {

    public MixinAbstractInventoryScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Shadow
    private void drawStatusEffectBackgrounds(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide){}

    @Shadow
    private void drawStatusEffectSprites(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide){}

    @Shadow
    private void drawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects){}

    @Shadow
    private Text getStatusEffectDescription(StatusEffectInstance statusEffect){ return Text.empty();}



    private void drawStatusEffects(DrawContext context, int mouseX, int mouseY) {
        int k = this.x + this.backgroundWidth + 2;
        int l = this.width - k;
        Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
        if(((PlayerMixinInterface)(this.client.player)).onMorphine()){
            //Strife3.LOGGER.info(String.valueOf(((PlayerMixinInterface)(this.client.player)).morphineTimer()));
            collection.removeIf(s -> s.equals(StatusEffects.POISON));
            collection.removeIf(s -> s.equals(StatusEffects.BLINDNESS));
            collection.removeIf(s -> s.equals(StatusEffects.SPEED));
            collection.removeIf(s -> s.equals(StatusEffects.SLOWNESS));
        }
        if(((PlayerMixinInterface)(this.client.player)).morphineTimer() > 30){
            collection.removeIf(s -> s.equals(StatusEffects.REGENERATION));
        }
        if(((PlayerMixinInterface)this.client.player).fullHeavyArmorCooldown()){
            collection.removeIf(s -> s.equals(StatusEffects.REGENERATION));
        }
        if(((PlayerMixinInterface)this.client.player).anyHeavyArmorCooldown()){
            collection.removeIf(s -> s.equals(StatusEffects.SLOWNESS));
            collection.removeIf(s -> s.equals(StatusEffects.WEAKNESS));
        }
        collection.removeIf(s -> s.equals(StatusEffects.MINING_FATIGUE));
        /*if(this.client.player.getInventory().getArmorStack(0).getItem() == ModItems.HYBRID_MASK || this.client.player.getInventory().getArmorStack(0).getItem() == ModItems.NIGHT_VISION_GOGGLES){
            collection.removeIf(s -> s.equals(StatusEffects.NIGHT_VISION));
        }*/
        if (!collection.isEmpty() && l >= 32) {
            boolean bl = l >= 120;
            int m = 33;
            if (collection.size() > 5) {
                m = 132 / (collection.size() - 1);
            }

            Iterable<StatusEffectInstance> iterable = Ordering.natural().sortedCopy(collection);
            this.drawStatusEffectBackgrounds(context, k, m, iterable, bl);
            this.drawStatusEffectSprites(context, k, m, iterable, bl);
            if (bl) {
                this.drawStatusEffectDescriptions(context, k, m, iterable);
            } else if (mouseX >= k && mouseX <= k + 33) {
                int n = this.y;
                StatusEffectInstance lv = null;

                for(Iterator var12 = iterable.iterator(); var12.hasNext(); n += m) {
                    StatusEffectInstance lv2 = (StatusEffectInstance)var12.next();
                    if (mouseY >= n && mouseY <= n + m) {
                        lv = lv2;
                    }
                }

                if (lv != null) {
                    List<Text> list = List.of(this.getStatusEffectDescription(lv), StatusEffectUtil.getDurationText(lv, 1.0F, this.client.world.getTickManager().getTickRate()));
                    context.drawTooltip(this.textRenderer, list, Optional.empty(), mouseX, mouseY);
                }
            }

        }
    }
}
