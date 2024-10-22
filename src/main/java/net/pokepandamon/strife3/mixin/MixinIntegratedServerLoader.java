package net.pokepandamon.strife3.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.BackupPromptScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IntegratedServerLoader.class)
public class MixinIntegratedServerLoader {
    @Shadow MinecraftClient client;

    /*private void showBackupPromptScreen(LevelStorage.Session session, boolean customized, Runnable callback, Runnable onCancel) {
        MutableText text;
        MutableText text2;
        if (customized) {
            text = Text.translatable("selectWorld.backupQuestion.customized");
            text2 = Text.translatable("selectWorld.backupWarning.customized");
            this.client.setScreen(new BackupPromptScreen(onCancel, (backup, eraseCache) -> {
                if (backup) {
                    EditWorldScreen.backupLevel(session);
                }

                callback.run();
            }, text, text2, false));
        }
    }*/

    @Redirect(method = {"checkBackupAndStart"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SaveProperties;getLifecycle()Lcom/mojang/serialization/Lifecycle;"))
    private Lifecycle removeAdviceOnLoad(SaveProperties instance) {
        return Lifecycle.stable();
    }
}
