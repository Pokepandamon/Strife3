package net.pokepandamon.strife3;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.pokepandamon.strife3.networking.AdminBooleanRequestC2SPayload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Strife3Config {

    // Example configuration options with default values
    public static boolean admin = false;
    public static int exampleInt = 5;

    // Path to the config file
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("strife3.json");

    // Save the configuration to the config file
    public static void saveConfig() {
        JsonObject config = new JsonObject();
        config.addProperty("admin", admin);
        config.addProperty("exampleInt", exampleInt);

        try {
            // Write the JSON config to the file
            Files.writeString(CONFIG_FILE, new Gson().toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ClientPlayNetworking.send(new AdminBooleanRequestC2SPayload(admin));
    }

    // Load the configuration from the config file
    public static void loadConfig() {
        if (Files.exists(CONFIG_FILE)) {
            try {
                // Read the config file as a string and parse it as JSON
                String json = Files.readString(CONFIG_FILE);
                JsonObject config = new Gson().fromJson(json, JsonObject.class);

                if (config.has("admin")) {
                    admin = config.get("admin").getAsBoolean();
                }
                if (config.has("exampleInt")) {
                    exampleInt = config.get("exampleInt").getAsInt();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();  // If the config file doesn't exist, create one with default values
        }
    }
}