package net.pokepandamon.strife3;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class Strife3ModMenuApiImpl implements ModMenuApi {
    /*@Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal(("title.examplemod.config"));

            builder.setSavingRunnable(() -> {
                // Serialise the config into the config file. This will be called last after all variables are updated.
            });
            ConfigCategory general = builder.getOrCreateCategory(Text.literal("category.strife3.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startStrField(Text.literal("option.examplemod.optionA"), currentValue)
                    .setDefaultValue("This is the default value") // Recommended: Used when user click "Reset"
                    .setTooltip(new TranslatableText("This option is awesome!")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> currentValue = newValue) // Recommended: Called when user save the config
                    .build()); // Builds the option entry for cloth config

            Screen screen = builder.build();
            MinecraftClient.getInstance().setScreen(screen);
        }
    }*/
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen(parent);
    }

    private Screen createConfigScreen(Screen parent) {
        // Create a new config builder
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.strife3.title"));

        // Create a category (can be multiple categories)
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.strife3.category.general"));

        // Create entries for the config options
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Example boolean option
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("config.strife3.option.admin"), Strife3Config.admin)
                .setDefaultValue(true) // Set default value
                .setSaveConsumer(newValue -> Strife3Config.admin = newValue) // Save the value
                .setTooltip(Text.translatable("config.strife3.option.admin.tooltip")) // Tooltip
                .build());

        builder.setSavingRunnable(() -> {
            Strife3Config.saveConfig();
        });

        /*// Example integer option
        general.addEntry(entryBuilder.startIntField(Text.translatable("config.strife3.option.exampleInt"), ExampleModConfig.exampleInt)
                .setDefaultValue(5) // Set default value
                .setSaveConsumer(newValue -> ExampleModConfig.exampleInt = newValue) // Save the value
                .setMin(0) // Optional: Set a minimum value
                .setMax(10) // Optional: Set a maximum value
                .build());

        // Create a button that opens another screen (optional)
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.strife3.option.exampleDescription"))
                .build());*/

        // Finish and return the screen
        return builder.build();
    }
}
