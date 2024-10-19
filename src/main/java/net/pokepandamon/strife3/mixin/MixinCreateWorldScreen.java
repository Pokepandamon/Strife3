package net.pokepandamon.strife3.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Mixin(CreateWorldScreen.class)
public class MixinCreateWorldScreen extends Screen {
	@Shadow private WorldCreator worldCreator;
	//@Shadow private Screen screen;

	@Shadow @Final private static Logger LOGGER;

	protected MixinCreateWorldScreen(Text title) {
		super(title);
	}

	private void createLevel() throws IOException {
		String worldFolderName = this.worldCreator.getWorldName().trim();
		File worldFolder = FabricLoader.getInstance().getConfigDir().resolve("saves").resolve(worldFolderName).toFile();
		File worldFolderTest = Path.of("C:\\Users\\swane\\Documents\\Moding\\Strife 3\\src\\main\\resources\\Strife3World").toFile();
		Path worldFolderTest2 = Path.of("C:\\Users\\swane\\Documents\\Moding\\Strife 3\\run");
		File worldFolderTest3 = worldFolderTest2.resolve("saves").resolve(worldFolderName).toFile();

		/*try {
			deleteDirectory(worldFolder);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}*/

		copyDirectory(worldFolderTest.getAbsolutePath(), worldFolderTest3.getAbsolutePath());

		this.client.createIntegratedServerLoader().start(this.worldCreator.getWorldName().trim(), () -> {
			//WorldListWidget.this.load();
			this.client.setScreen(this);
		});
	}

	private static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
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
	}
}