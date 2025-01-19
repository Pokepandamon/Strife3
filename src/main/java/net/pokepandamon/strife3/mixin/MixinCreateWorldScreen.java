package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.WorldCreatorMixinInterface;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.zip.GZIPInputStream;

@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen extends Screen {
	@Shadow private WorldCreator worldCreator;
	//@Shadow private Screen screen;

	@Shadow @Final private static Logger LOGGER;

	@Shadow public abstract WorldCreator getWorldCreator();

	protected MixinCreateWorldScreen(Text title) {
		super(title);
	}

	/*private void createLevel() throws IOException {
		String worldFolderName = this.worldCreator.getWorldName().trim();
		File worldFolder = FabricLoader.getInstance().getConfigDir().resolve("saves").resolve(worldFolderName).toFile();

		Identifier resourcePath = Identifier.of(Strife3.MOD_ID,"Strife3World"); // Adjust as needed
		ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();

		try {
			Resource resource = resourceManager.getResource(resourcePath).orElseThrow();
			copyDirectory(resource.getInputStream()., worldFolder.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		File worldFolderTest = Path.of("C:\\Users\\swane\\Documents\\Moding\\Strife 3\\src\\main\\resources\\Strife3World").toFile();
		Path worldFolderTest2 = Path.of("C:\\Users\\swane\\Documents\\Moding\\Strife 3\\run");

		try {
			deleteDirectory(worldFolder);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		this.client.createIntegratedServerLoader().start(this.worldCreator.getWorldName().trim(), () -> {
			//WorldListWidget.this.load();
			this.client.setScreen(this);
		});
	}*/

	/*private static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
			throws IOException {
		Files.walk(Paths.get(sourceDirectoryLocation))
				.forEach(source -> {
					LOGGER.info(String.valueOf(Paths.get(sourceDirectoryLocation)));
					Path destination = Paths.get(destinationDirectoryLocation, source.toString()
							.substring(sourceDirectoryLocation.length()));
					try {
						Files.copy(source, destination);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}*/
	@Inject(method = "<init>", at = @At("TAIL"))
	private void setDefaultGamerules(CallbackInfo ci){
		this.worldCreator.getGameRules().get(GameRules.SPAWN_RADIUS).set(0, null);
	}

	@Inject(method = "createLevel", at = @At(value = "HEAD"))
	private void customLevelGeneration(CallbackInfo ci) {
		//LOGGER.info(((WorldCreatorMixinInterface) this.getWorldCreator()).getStrife3World().toString());
		//return new LevelInfo(string, this.worldCreator.getGameMode().defaultGameMode, this.worldCreator.isHardcore(), this.worldCreator.getDifficulty(), this.worldCreator.areCheatsEnabled(), this.worldCreator.getGameRules(), this.worldCreator.getGeneratorOptionsHolder().dataConfiguration());
		if (((WorldCreatorMixinInterface) this.getWorldCreator()).getStrife3World().booleanValue()) {
			String newWorldName = this.worldCreator.getWorldName().trim();
			String originalNewWorldName = this.worldCreator.getWorldName().trim();

			// 1. Get the saves directory
			Path savesDirectory = this.client.getLevelStorage().getSavesDirectory();

			// 2. Define the path for the new world
			Path newWorldPath = savesDirectory.resolve(newWorldName);

			int numberOfFiles = 0;
			if (newWorldPath.toFile().isDirectory()) {
				numberOfFiles = 1;
				while (savesDirectory.resolve(newWorldName + " (" + numberOfFiles + ")").toFile().isDirectory()) {
					numberOfFiles++;
				}
				newWorldPath = savesDirectory.resolve(newWorldName + " (" + numberOfFiles + ")");
				Strife3.LOGGER.info("File Backup? " + numberOfFiles);
			}

			if (numberOfFiles != 0) {
				newWorldName = newWorldName + " (" + numberOfFiles + ")";
			}

			// 3. Get the mod resources directory (using Fabric API)
			Path modResourcesPath = FabricLoader.getInstance().getModContainer(Strife3.MOD_ID)
					.orElseThrow(() -> new RuntimeException("Could not find your mod container!"))
					.getRootPaths().get(0); // Adjust the path as needed

			// 4. Define the path to the template world within the mod resources
			Path templateWorldPath = modResourcesPath.resolve("Strife3World"); // Adjust 'template_world' to your template folder name

			// 5. Check if the template world exists
			if (!Files.exists(templateWorldPath)) {
				Strife3.LOGGER.info("Template world not found at: " + templateWorldPath);
				// Handle error appropriately (e.g., display an error message to the user)
				return;
			}

			// 6. Copy the template world to the saves directory
			try {
				FileUtils.copyDirectory(templateWorldPath.toFile(), newWorldPath.toFile());

				// 7. Rename level.dat inside the new world folder if necessary to change the display name.
				// First, find level.dat within the newly copied world
				Path newLevelDat = newWorldPath.resolve("level.dat");

				// Rename the file
				if (Files.exists(newLevelDat)) {
					// If level.dat exists, the copy operation was successful. No need to rename for the directory copy
					Strife3.LOGGER.info("World successfully copied and level.dat found. Consider updating level.dat for display name if necessary.");

					NbtCompound rootTag;
					try (FileInputStream fileInputStream = new FileInputStream(newLevelDat.toFile())) {
						// Create an NbtSizeTracker with a limit (e.g., 2097152 bytes = 2MB)
						// You can adjust this limit, but it's important to have one
						NbtSizeTracker sizeTracker = new NbtSizeTracker(2097152, 500);
						rootTag = NbtIo.readCompressed(fileInputStream, sizeTracker);
					}

					if (rootTag.contains("Data", NbtElement.COMPOUND_TYPE)) {
						NbtCompound dataTag = rootTag.getCompound("Data");

						// 4. Check if "LevelName" exists and is a string, or doesn't exist
						if (!dataTag.contains("LevelName") || dataTag.getType("LevelName") == NbtElement.STRING_TYPE) {
							dataTag.putString("LevelName", originalNewWorldName);

							// 5. Write the modified NBT data back to the file (compressed)
							try (FileOutputStream fileOutputStream = new FileOutputStream(newLevelDat.toFile())) {
								NbtIo.writeCompressed(rootTag, fileOutputStream);
							}

							Strife3.LOGGER.info("World name changed successfully!");
						} else {
							Strife3.LOGGER.info("Error: 'LevelName' key exists but is not a string. Cannot safely overwrite.");
						}

						if (!dataTag.contains("allowCommands") || dataTag.getType("allowCommands") == NbtElement.BYTE_TYPE) {
							if (this.worldCreator.areCheatsEnabled()) {
								dataTag.putByte("allowCommands", (byte) 1);
							} else {
								dataTag.putByte("allowCommands", (byte) 0);
							}


							// 5. Write the modified NBT data back to the file (compressed)
							try (FileOutputStream fileOutputStream = new FileOutputStream(newLevelDat.toFile())) {
								NbtIo.writeCompressed(rootTag, fileOutputStream);
							}

							Strife3.LOGGER.info("Allow Commands updated");
						} else {
							Strife3.LOGGER.info("Error: 'allowCommands' key exists but is not a byte. Cannot safely overwrite.");
						}

						if (!dataTag.contains("GameType") || dataTag.getType("GameType") == NbtElement.INT_TYPE) {
							dataTag.putInt("GameType", this.worldCreator.getGameMode().defaultGameMode.getId());


							// 5. Write the modified NBT data back to the file (compressed)
							try (FileOutputStream fileOutputStream = new FileOutputStream(newLevelDat.toFile())) {
								NbtIo.writeCompressed(rootTag, fileOutputStream);
							}

							Strife3.LOGGER.info("Default Gamemode updated");
						} else {
							Strife3.LOGGER.info("Error: 'GameType' key exists but is not an integer. Cannot safely overwrite.");
						}

						if (!dataTag.contains("Difficulty") || dataTag.getType("Difficulty") == NbtElement.BYTE_TYPE) {
							dataTag.putByte("Difficulty", (byte) this.worldCreator.getDifficulty().getId());


							// 5. Write the modified NBT data back to the file (compressed)
							try (FileOutputStream fileOutputStream = new FileOutputStream(newLevelDat.toFile())) {
								NbtIo.writeCompressed(rootTag, fileOutputStream);
							}

							Strife3.LOGGER.info("Difficulty updated");
						} else {
							Strife3.LOGGER.info("Error: 'Difficulty' key exists but is not a byte. Cannot safely overwrite.");
						}

						if (!dataTag.contains("GameRules") || dataTag.getType("GameRules") == NbtElement.COMPOUND_TYPE) {
							dataTag.put("GameRules", this.worldCreator.getGameRules().toNbt());


							// 5. Write the modified NBT data back to the file (compressed)
							try (FileOutputStream fileOutputStream = new FileOutputStream(newLevelDat.toFile())) {
								NbtIo.writeCompressed(rootTag, fileOutputStream);
							}

							Strife3.LOGGER.info("Game Rules updated");
						} else {
							Strife3.LOGGER.info("Error: 'GameRules' key exists but is not a byte. Cannot safely overwrite.");
						}

					} else {
						Strife3.LOGGER.info("Error: 'Data' compound tag not found in level.dat.");
					}


				} else {
					Strife3.LOGGER.info("Failed to find level.dat in the copied world. The world might not have been copied correctly.");
					// Handle this error appropriately. Perhaps try copying again or alert the user.
				}

				Strife3.LOGGER.info("World created successfully from template!");
			} catch (IOException e) {
				Strife3.LOGGER.info("Failed to create world from template: " + e.getMessage());
				// Handle error appropriately (e.g., display an error message to the user)
				return;
			}

			// --- End of Mixin Injection ---

			// ... (rest of the original createLevel() method, if any) ...

			// Example on how to open the world if needed
			this.client.createIntegratedServerLoader().start(newWorldName, () -> {
				//WorldListWidget.this.load();
				this.client.setScreen(this);
			});

			return;
		}
	}
}