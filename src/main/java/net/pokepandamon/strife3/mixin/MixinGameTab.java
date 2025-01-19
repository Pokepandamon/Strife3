package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.pokepandamon.strife3.WorldCreatorMixinInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Mixin(targets = "net.minecraft.client.gui.screen.world.CreateWorldScreen$GameTab")
@Mixin(CreateWorldScreen.GameTab.class)
public class MixinGameTab extends GridScreenTab {
	@Shadow
	@Final
	private TextFieldWidget worldNameField;

	@Final
	@Shadow
	CreateWorldScreen field_42174;

	public MixinGameTab(Text title) {
		super(title);
	}

	@Inject(at = @At("TAIL"), method = "<init>")
	public void init(CallbackInfo info, @Local(ordinal = 0) GridWidget.Adder adder) {
		//CreateWorldScreen enclosingInstance = ((CreateWorldScreen) (Object) this);
		CreateWorldScreen enclosingInstance = field_42174;
		//GridWidget.Adder adder = this.grid.setRowSpacing(8).createAdder(1);

		// Create a new button
		CyclingButtonWidget<Boolean> strife3GenerationButton = CyclingButtonWidget.onOffBuilder()
				.tooltip(value -> Tooltip.of(Text.literal("This controls whether the generated world is a Strife world")))
				.build(0, 84, 210, 20, Text.literal("Strife 3 Generation"), (button, value) -> {
					// Toggle logic
					((WorldCreatorMixinInterface) enclosingInstance.getWorldCreator()).setStrife3World(value);
				});

		// Set the initial value based on your custom storage
		strife3GenerationButton.setValue(((WorldCreatorMixinInterface) enclosingInstance.getWorldCreator()).getStrife3World());

		// Add the button to the UI
		adder.add(strife3GenerationButton);
	}
}