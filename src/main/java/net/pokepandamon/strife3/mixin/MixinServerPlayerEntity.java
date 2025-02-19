package net.pokepandamon.strife3.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pokepandamon.strife3.*;
import net.pokepandamon.strife3.items.ModItems;
import net.pokepandamon.strife3.music.Ambient;
import net.pokepandamon.strife3.music.Song;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends MixinEntityPlayer implements ServerPlayerMixinInterface {

    @Shadow @Final private static Logger LOGGER;

    @Shadow public ServerPlayNetworkHandler networkHandler;

    @Shadow public abstract void playSoundToPlayer(SoundEvent sound, SoundCategory category, float volume, float pitch);

    @Shadow public abstract void sendMessage(Text message);

    @Shadow public abstract ServerWorld getServerWorld();

    @Shadow public abstract void teleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch);

    @Unique private boolean seenZoneChange = false;
    @Unique private boolean clientAdminValue;
    @Unique private boolean underwaterAmbiencePlaying = true;
    @Unique private int ambientTimer = 0;
    @Unique private int songTimer = 600;
    @Unique private ItemStack[] pastArmor;
    @Unique private int depthsEnteredText = 0;
    @Unique private int deepOpticEnteredText = 0;
    @Unique private int deepOpticLeftText = 0;
    @Unique private int factoryEnteredText = 0;
    @Unique private boolean updateWorld = false;
    @Unique private int[] totalProgress = new int[2];
    @Unique private int[] chunkProgress = new int[3];
    @Unique private static Path modResourcesPath = FabricLoader.getInstance().getModContainer(Strife3.MOD_ID)
            .orElseThrow(() -> new RuntimeException("Could not find your mod container!"))
            .getRootPaths().get(0);
    @Unique private static Path schematicsPath;
    @Unique private static Map<String, Map<String, String>> replacementKeys = WorldUpdaterMaps.replacementKeys;
    @Unique private static Map<String, String> globalReplacementKeys = WorldUpdaterMaps.globalReplacementKeys;
    //@Unique private static Map<String, String> deepCoralReplacement;
    @Unique private ArrayList<SchematicReference> schematics = new ArrayList<>();
    @Unique private int schematicSize;
    @Unique private static int schematicReplacementStartX = Strife3.schematicReplacementStartX;
    @Unique private static int schematicReplacementStartZ = Strife3.schematicReplacementStartZ;
    @Unique private static int schematicReplacementEndX = Strife3.schematicReplacementEndX;
    @Unique private static int schematicReplacementEndZ = Strife3.schematicReplacementEndZ;
    @Unique private static int schematicReplacementMaxY = Strife3.schematicReplacementMaxY;
    @Unique private static int totalChunks;
    @Unique private int finishedChunks;
    @Unique private LocalDateTime replacementStartTime;
    @Unique private int replacedSchematics;
    @Unique private ArrayList<ArrayList<ArrayList<ArrayList<SchematicReference.SchematicSearchResult>>>> schematicResults = new ArrayList<>(118);

    static{
        schematicsPath = modResourcesPath.resolve("data/strife3/schematics");

        totalChunks = (schematicReplacementEndZ - schematicReplacementStartZ + 1);
    }

//
    protected MixinServerPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyExpressionValue(
            method = "getWorldSpawnPos",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/dimension/DimensionType;hasSkyLight()Z"
            )
    )
    private boolean modifyHasSkyLight(boolean original) {
        boolean myCustomCondition = false; // your custom logic here
        return original && myCustomCondition; // Modify the condition here
    }

    @Override
    public void customServerPlayerTick(){
        this.locationTick();
        this.soundTick();
        this.armorDeEquipTick();
        this.loreReadout();
        if(this.updateWorld){
            this.worldUpdaterTick();
        }
    }

    @Override
    public void locationTick(){
        //LOGGER.info(String.valueOf(this.getWorld().getDimension().effects()));
        if(Strife3.netherideFactory.inArea(this)){
            WorldFlags worldFlags = WorldFlags.getServerState(this.getServer());
            if (!worldFlags.factoryEntered) {
                worldFlags.factoryEntered = true;
                this.factoryEnteredText = 4 * 20 * 3 + 20;
            }
        }
        if(super.currentZone != null) {
            if (super.currentZone.equals("Depths")) {
                WorldFlags worldFlags = WorldFlags.getServerState(this.getServer());
                if (!worldFlags.depthsEntered) {
                    worldFlags.depthsEntered = true;
                    this.depthsEnteredText = 4 * 20 * 3 + 20;
                }
                if(!worldFlags.deepOpticLeft && this.getInventory().contains(ModItems.DATA_CORE.getDefaultStack())){
                    worldFlags.deepOpticLeft = true;
                    this.deepOpticLeftText = 4 * 20 * 2 + 20;
                }
            }
            if (super.currentZone.equals("DeepOptic")) {
                WorldFlags worldFlags = WorldFlags.getServerState(this.getServer());
                if (!worldFlags.deepOpticEntered) {
                    worldFlags.deepOpticEntered = true;
                    this.deepOpticEnteredText = 4 * 20 * 2 + 20;
                }
            }
            if (this.inDeepOptic()) {
                ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(new GameMessageS2CPacket(Text.literal("Depth: " + (255 - ((Double) this.getY()).intValue() + 555) + " meters"), true));
            } else {
                //LOGGER.info("X: " + this.getX() + " Y: " + this.getY() + " Z: " + this.getZ() + " RX: " + ((Double) this.getX()).intValue() + " RY: " + ((Double) this.getY()).intValue() + " RZ: " + ((Double) this.getZ()).intValue() + " Zone: " + super.currentZone + " Seen: " + seenZoneChange);
                if (seenZoneChange) {
                    seenZoneChange = !(this.getY() <= 90 || (this.getY() >= 110 && this.getY() <= 140) || this.getY() >= 160);
                }
                if (!seenZoneChange && ((((Double) this.getY()).intValue() == 100) || (((Double) this.getY()).intValue() == 150))) {
                    seenZoneChange = true;

                    Text title;
                    Text subtitle;

                    if (((Double) this.getY()).intValue() == 100) {
                        if (super.currentZone.equals("Lush")) {
                            title = Text.literal("You are entering the Depths").formatted(Formatting.BOLD, Formatting.DARK_PURPLE);
                            subtitle = Text.literal("Your mining speed has been decreased").formatted(Formatting.GRAY);
                        } else {
                            title = Text.literal("You are entering the Lush").formatted(Formatting.BOLD, Formatting.DARK_GREEN);
                            subtitle = Text.literal("Your mining speed has been increased").formatted(Formatting.GRAY);
                        }
                    } else {
                        if (super.currentZone.equals("Shallows")) {
                            title = Text.literal("You are entering the Lush").formatted(Formatting.BOLD, Formatting.DARK_GREEN);
                            subtitle = Text.literal("Your mining speed has been decreased").formatted(Formatting.GRAY);
                        } else {
                            title = Text.literal("You are entering the Shallows").formatted(Formatting.BOLD, Formatting.AQUA);
                            subtitle = Text.literal("Your mining speed has been increased").formatted(Formatting.GRAY);
                        }
                    }

                    //net.minecraft.network.packet.s2c.play.

                    TitleS2CPacket titlePacket = new TitleS2CPacket(title);
                    SubtitleS2CPacket subtitlePacket = new SubtitleS2CPacket(subtitle);

                    ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(titlePacket);
                    ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(subtitlePacket);
                }
                //LOGGER.info("Inside Water: " + this.isInsideWaterOrBubbleColumn() + " | My Check" + this.getWorld().getBlockState(new BlockPos((int) this.getX(), (int)this.getY(), (int)this.getZ())));
                if (this.isInsideWaterOrBubbleColumn()) {
                    //LOGGER.info("I WAS HERE");
                    ((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(new GameMessageS2CPacket(Text.literal("Depth: " + (255 - ((Double) this.getY()).intValue()) + " meters"), true));
                }
            }
        }
        super.locationTick();
    }

    @Inject(method= "tick", at=@At("HEAD"))
    public void tick(CallbackInfo ci){
        this.customServerPlayerTick();
    }

    @Override
    public void setClientAdminValue(boolean newClientAdminValue){
        this.clientAdminValue = newClientAdminValue;
    }

    @Override
    public boolean getClientAdminValue(){
        return clientAdminValue;
    }

    @Override
    public void soundTick(){
        this.songTick();
        this.ambientTick();
        //LOGGER.info("Ambient Timer: " + ambientTimer + " Song Timer: " + songTimer);
    }

    @Override
    public void songTick(){
        if(this.songTimer == 0) {
            if (!this.currentZone.equals("DeepOptic")) {
                SoundEvent songToPlay = null;
                if (this.currentZone.equals("Shallows")) {
                    songToPlay = Song.SHALLOWS;
                } else if (this.currentZone.equals("Lush")) {
                    songToPlay = Song.LUSH;
                } else if (this.currentZone.equals("Depths")) {
                    songToPlay = Song.DEEP;
                }

                //this.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(songToPlay), SoundCategory.MUSIC, this.getX(), this.getY(), this.getZ(), 1F, 1F, this.random.nextLong()));
                this.playSoundToPlayer(songToPlay, SoundCategory.MUSIC, 1F, 1F);

                // 3min 45sec + 1-3min
                this.songTimer = 4500 + this.random.nextBetween(1200, 3600);
            }else{
                this.playSoundToPlayer(Song.DEEP_OPTIC, SoundCategory.MUSIC, 1F, 1F);
                this.songTimer = 9800;
            }
        }
        songTimer--;
    }

    public void setSongTimer(int newTimer){
        this.songTimer = newTimer;
    }

    @Override
    public void ambientTick(){
        StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(null, SoundCategory.AMBIENT);
        if(this.isSubmergedInWater && !this.underwaterAmbiencePlaying && ambientTimer > 0){
            this.networkHandler.sendPacket(stopSoundS2CPacket);
            this.ambientTimer = 0;
        }else if(!this.isSubmergedInWater && this.underwaterAmbiencePlaying && ambientTimer > 0){
            this.networkHandler.sendPacket(stopSoundS2CPacket);
            this.ambientTimer = 0;
        }
        if(this.ambientTimer == 0){
            SoundEvent ambientToPlay;
            if(this.isSubmergedInWater){
                if(this.currentZone.equals("Shallows")){
                    ambientToPlay = Ambient.SHALLOWS;
                }else if(this.currentZone.equals("Lush")){
                    ambientToPlay = Ambient.LUSH;
                }else if(this.currentZone.equals("Depths")){
                    ambientToPlay = Ambient.DEEP;
                }else{
                    ambientToPlay = Ambient.DEEP_OPTIC_UNDERWATER;
                }
                underwaterAmbiencePlaying = true;
            }else{
                if(this.currentZone.equals("DeepOptic")){
                    ambientToPlay = Ambient.DEEP_OPTIC_ABOVE_WATER;
                }else{
                    ambientToPlay = Ambient.ABOVE_WATER;
                }
                underwaterAmbiencePlaying = false;
            }
            //this.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(ambientToPlay), SoundCategory.AMBIENT, this.getX(), this.getY(), this.getZ(), 1F, 1F, this.random.nextLong()));
            this.playSoundToPlayer(ambientToPlay, SoundCategory.AMBIENT, 1F, 1F);
            this.ambientTimer = 2890;
        }
        ambientTimer--;
    }

    @Override
    public void armorDeEquipTick(){
        if(pastArmor == null){
            pastArmor = new ItemStack[]{
                    this.getInventory().getArmorStack(3),  // helmet slot
                    this.getInventory().getArmorStack(2),  // chestplate slot
                    this.getInventory().getArmorStack(1),  // leggings slot
                    this.getInventory().getArmorStack(0)   // boots slot
            };
        }
        ItemStack[] currentArmor = new ItemStack[]{
                this.getInventory().getArmorStack(3),  // helmet slot
                this.getInventory().getArmorStack(2),  // chestplate slot
                this.getInventory().getArmorStack(1),  // leggings slot
                this.getInventory().getArmorStack(0)   // boots slot
        };
        if(!pastArmor[0].equals(currentArmor[0])){
            LOGGER.info("I'm here");
            LOGGER.info(pastArmor[0].getItem().getTranslationKey());
            if(pastArmor[0].getItem().equals(ModItems.HYBRID_MASK) || pastArmor[0].getItem().equals(ModItems.DIVERS_MASK) || pastArmor[0].getItem().equals(ModItems.NIGHT_VISION_GOGGLES)){
                LOGGER.info("I got this far");
                this.removeStatusEffect(StatusEffects.NIGHT_VISION);
            }
            if(pastArmor[0].getItem().equals(ModItems.HEAVY_DIVERS_MASK)){
                this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION));
                if(!currentArmor[1].equals(ModItems.HEAVY_DIVERS_CHESTPLATE) && !currentArmor[2].equals(ModItems.HEAVY_DIVERS_LEGGINGS) && !currentArmor[3].equals(ModItems.HEAVY_DIVERS_BOOTS)){
                    this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF));
                }
            }
        }
        if(!pastArmor[1].equals(currentArmor[1])){
            if(pastArmor[1].getItem().equals(ModItems.HEAVY_DIVERS_CHESTPLATE)) {
                this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION));
                if(!currentArmor[0].equals(ModItems.HEAVY_DIVERS_MASK) && !currentArmor[2].equals(ModItems.HEAVY_DIVERS_LEGGINGS) && !currentArmor[3].equals(ModItems.HEAVY_DIVERS_BOOTS)){
                    this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF));
                }
            }
        }
        if(!pastArmor[2].equals(currentArmor[2])){
            if(pastArmor[2].getItem().equals(ModItems.HEAVY_DIVERS_LEGGINGS)) {
                this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION));
                if(!currentArmor[1].equals(ModItems.HEAVY_DIVERS_CHESTPLATE) && !currentArmor[0].equals(ModItems.HEAVY_DIVERS_MASK) && !currentArmor[3].equals(ModItems.HEAVY_DIVERS_BOOTS)){
                    this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF));
                }
            }
        }
        if(!pastArmor[3].equals(currentArmor[3])){
            if(pastArmor[3].getItem().equals(ModItems.HEAVY_DIVERS_BOOTS)) {
                this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_REGENERATION));
                if(!currentArmor[1].equals(ModItems.HEAVY_DIVERS_CHESTPLATE) && !currentArmor[2].equals(ModItems.HEAVY_DIVERS_LEGGINGS) && !currentArmor[0].equals(ModItems.HEAVY_DIVERS_MASK)){
                    this.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(Strife3.HEAVY_DIVER_DEBUFF));
                }
            }
        }
        pastArmor = new ItemStack[]{
                this.getInventory().getArmorStack(3),  // helmet slot
                this.getInventory().getArmorStack(2),  // chestplate slot
                this.getInventory().getArmorStack(1),  // leggings slot
                this.getInventory().getArmorStack(0)   // boots slot
        };
    }

    private void loreReadout(){
        if(this.depthsEnteredText > 0){
            if(this.depthsEnteredText == 260){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal("UNICRI COMMAND:").setStyle(Style.EMPTY.withColor(0x009edb)));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" The trenches in this area have an unprecedented amount of dead organics in them, far more than we could naturally expect. "}]
                }
            }else if(this.depthsEnteredText == 240){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" The trenches in this area have an unprecedented amount of dead organics in them, far more than we could naturally expect. "));
                    //tellraw @a {"text":"From our scans we have also identified several openings to an extensive cave system in the trench walls."}
                }
            }else if(this.depthsEnteredText == 160){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" From our scans we have also identified several openings to an extensive cave system in the trench walls."));
                    //tellraw @a {"text":"From our scans we have also identified several openings to an extensive cave system in the trench walls."}
                }
            }else if(this.depthsEnteredText == 80){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Whatever caused this buildup of dead matter must have originated from inside those caves."));
                    //tellraw @a {"text":"Whatever caused this buildup of dead matter must have originated from inside those caves. "}
                }
            }else if(this.depthsEnteredText == 1){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" There seems to be more to this whole debacle than we initially anticipated. Good luck."));
                    //tellraw @a {"text":"There seems to be more to this whole debacle than we initially anticipated. Good luck."}
                }
            }
            depthsEnteredText--;
        }
        if(this.deepOpticLeftText > 0){
            if(this.deepOpticLeftText == 180){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal("UNICRI COMMAND:").setStyle(Style.EMPTY.withColor(0x009edb)));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" We have regained contact! Good to hear from you again, we were beginning to worry something had happened.\n"}]
                }
            }else if(this.deepOpticLeftText == 160){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" We have regained contact! Good to hear from you again, we were beginning to worry something had happened."));
                    //tellraw @a {"text":"We see you have obtained some type of data core from within the facility. With whatever information it contains, we should have enough data to finally piece this whole story together. \n"}
                }
            }else if(this.deepOpticLeftText == 80){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" We see you have obtained some type of data core from within the facility. With whatever information it contains, we should have enough data to finally piece this whole story together."));
                    //tellraw @a {"text":"From our scans we have also identified several openings to an extensive cave system in the trench walls."}
                }
            }else if(this.deepOpticLeftText == 1){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Bring it up to the research lab on the surface islands and plug it into the terminal. We're sending a lift out of this place for your team shortly."));
                    //tellraw @a {"text":"Bring it up to the research lab on the surface islands and plug it into the terminal. We\u2019re sending a lift out of this place for your team shortly.\n"}
                }
            }
            deepOpticLeftText--;
        }
        if(this.deepOpticEnteredText > 0){
            if(this.deepOpticEnteredText == 180){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal("UNICRI COMMAND:").setStyle(Style.EMPTY.withColor(0x009edb)));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" DeepOptic\u2019s operations here were far larger than we could have ever expected. This structure, at least from initial scans, is massive."}]
                }
            }else if(this.deepOpticEnteredText == 160){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" DeepOptic's operations here were far larger than we could have ever expected. This structure, at least from initial scans, is massive."));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" DeepOptic\u2019s operations here were far larger than we could have ever expected. This structure, at least from initial scans, is massive."}]
                }
            }else if(this.deepOpticEnteredText == 80){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" If you proceed farther into the facility, be aware that we will no longer be able to contact you due to your depth. We still encourage you to continue, however, and attempt to find out exactly what DeepOptic was carrying out here."));
                    //tellraw @a {"text":"If you proceed farther into the facility, be aware that we will no longer be able to contact you due to your depth. We still encourage you to continue, however, and attempt to find out exactly what DeepOptic was carrying out here.\n"}
                }
            }else if(this.deepOpticEnteredText == 1){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Good luck."));
                    //tellraw @a {"text":"Good luck.\n"}
                }
            }
            deepOpticEnteredText--;
        }
        if(this.factoryEnteredText > 0){
            if(this.factoryEnteredText == 260){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal("UNICRI COMMAND:").setStyle(Style.EMPTY.withColor(0x009edb)));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" Well, this is unexpected. Upon analysis, this structure, as well as the many advanced submersible wrecks around these caves, seem to all be traceable back to the biotech company DeepOptic."},{"text":" ","color":"#FF0000"}]
                }
            }else if(this.factoryEnteredText == 240){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Well, this is unexpected. Upon analysis, this structure, as well as the many advanced submersible wrecks around these caves, seem to all be traceable back to the biotech company DeepOptic."));
                    //tellraw @a ["",{"text":"UNICRI COMMAND:","color":"#009EDB"},{"text":" Well, this is unexpected. Upon analysis, this structure, as well as the many advanced submersible wrecks around these caves, seem to all be traceable back to the biotech company DeepOptic."},{"text":" ","color":"#FF0000"}]
                }
            }else if(this.factoryEnteredText == 160){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" DeepOptic was pretty secretive about its operations after it scored a contract with the US military back in 1977. Publically, it was never involved with the government, and sold to some other company just a few years later, landing the CEO a hefty profit."));
                    //tellraw @a {"text":"DeepOptic was pretty secretive about its operations after it scored a contract with the US military back in 1977. Publically, it was never involved with the government, and sold to some other company just a few years later, landing the CEO a hefty profit. "}
                }
            }else if(this.factoryEnteredText == 80){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Clearly that's not the whole story. Somehow, DeepOptic remained active far after the supposed company sale, and decided to make this place a staging ground for whatever they were working on."));
                    //tellraw @a {"text":"Clearly that\u2019s not the whole story. Somehow, DeepOptic remained active far after the supposed company sale, and decided to make this place a staging ground for whatever they were working on."}
                }
            }else if(this.factoryEnteredText == 1){
                for (ServerPlayerEntity player : this.getServer().getPlayerManager().getPlayerList()){
                    player.sendMessage(Text.literal(" Maybe there's some records that can help uncover this story somewhere in these wrecks."));
                    //tellraw @a {"text":"Maybe there\u2019s some records that can help uncover this story somewhere in these wrecks."}
                }
            }
            factoryEnteredText--;
        }
    }

    public void startWorldUpdater() throws IOException {
        /*if(!this.updateWorld){
            this.totalProgress[0] = -56;
            this.totalProgress[1] = -56;
            this.chunkProgress[0] = 0;
            this.chunkProgress[1] = 0;
            this.chunkProgress[2] = 0;
        }*/
        if(!this.updateWorld) {
            this.totalProgress[0] = schematicReplacementStartX;
            this.totalProgress[1] = schematicReplacementStartZ;
            this.chunkProgress[0] = 0;
            this.chunkProgress[1] = 0;
            this.chunkProgress[2] = 0;
        }

        // 4. Define the path to the template world within the mod resources
        if(!this.updateWorld) {
            for (File file : schematicsPath.toFile().listFiles()) {
                NbtSizeTracker sizeTracker = new NbtSizeTracker(2097152, 500);
                try {
                    Strife3.LOGGER.info(file.getName());
                    if (replacementKeys.containsKey(file.getName())) {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys, replacementKeys.get(file.getName()),file.getName()));
                    } else {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys,file.getName()));
                    }
                /*if(nup.getReplacement().size() > 0){
                    Strife3.LOGGER.info("X-Offset " + nup.getReplacement().get(0).getOffsetX() + " Y-Offset " + nup.getReplacement().get(0).getOffsetY() + " Z-Offset " + nup.getReplacement().get(0).getOffsetZ() + " Blockstate " + nup.getReplacement().get(0).getBlockState());
                }*/
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            this.schematicSize = this.schematics.size();
        }

        if(!this.updateWorld) {
            this.updateWorld = true;
        }else{
            this.updateWorld = false;
        }

        this.finishedChunks = 0;

        this.replacementStartTime = LocalDateTime.now();
        this.replacedSchematics = 0;

        for(int s = 0; s < schematicSize; s++) {
            ArrayList<ArrayList<ArrayList<SchematicReference.SchematicSearchResult>>> newSChunk = new ArrayList<>(4);
            for (int k = 0; k < 4; k++) {
                ArrayList<ArrayList<SchematicReference.SchematicSearchResult>> newZChunk = new ArrayList<>((schematicReplacementEndX - schematicReplacementStartX + 1));
                for (int i = 0; i < (schematicReplacementEndX - schematicReplacementStartX + 1); i++) {
                    ArrayList<SchematicReference.SchematicSearchResult> newXChunk = new ArrayList<>(schematicReplacementMaxY);
                    newZChunk.add(newXChunk);
                }
                newSChunk.add(newZChunk);
            }
            schematicResults.add(newSChunk);
        }
        System.gc();

        /*try {
            // Read the NBT data from the file
            NbtCompound nbtData = NbtIo.readCompressed(nbtFile);

            // Access the data
            String exampleData = nbtData.getString("ExampleKey");
            System.out.println("ExampleKey value: " + exampleData);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read the NBT file!");
        }*/
    }

    public void worldUpdaterTick(){
        //Strife3.LOGGER.info("Started Processing " + (this.totalProgress[0] * 16 + this.chunkProgress[0]) + "," + (this.totalProgress[1] * 16 + this.chunkProgress[1]));
        //this.setPos(this.totalProgress[0] * 16 + this.chunkProgress[0], 256, this.totalProgress[1] * 16 + this.chunkProgress[1]);
        this.teleport(this.getServerWorld(), this.totalProgress[0], 256, this.totalProgress[1], 0F, -90F);
        for(int i = 1; i < schematicReplacementMaxY; i++){
            if(this.totalProgress[0] == schematicReplacementStartX && this.totalProgress[1] == schematicReplacementStartZ){
                Strife3.LOGGER.info("Starting Processing Block " + i);
            }
            ArrayList<SchematicReference.SchematicSearchResult> foundSchematics = new ArrayList<>();
            for(int j = 0; j < this.schematicSize; j++){
                SchematicReference schematic = this.schematics.get(j);
                SchematicReference.SchematicSearchResult currentSchematicResult = schematic.checkPositionAndSurroundings(new BlockPos(this.totalProgress[0], i, this.totalProgress[1]),this.getServerWorld(), this.schematicResults.get(j));
                if(currentSchematicResult.result()){
                    foundSchematics.add(currentSchematicResult);
                }
                //Strife3.LOGGER.info("Checked Schematic " + j + "/" + this.schematicSize);
            }
            this.replacedSchematics += foundSchematics.size();
            if(foundSchematics.size() == 1){
                foundSchematics.get(0).schematic().replacePosition(new BlockPos(this.totalProgress[0], i, this.totalProgress[1]),this.getServerWorld(),foundSchematics.get(0).transformation());
            }if(foundSchematics.size() > 1){
                SchematicReference.SchematicSearchResult currentMax = foundSchematics.get(0);
                for(int k = 1; k < foundSchematics.size(); k++){
                    if(foundSchematics.get(k).blockPercentage() > currentMax.blockPercentage()){
                        currentMax = foundSchematics.get(k);
                    }
                }

                currentMax.schematic().replacePosition(new BlockPos(this.totalProgress[0], i, this.totalProgress[1]),this.getServerWorld(),currentMax.transformation());
            }
            //Strife3.LOGGER.info("Processed: " + i + "/50");
        }

        if(this.totalProgress[0] == schematicReplacementEndX){
            if(this.totalProgress[1] == schematicReplacementEndZ){
                this.updateWorld = false;
            }else{
                this.finishedChunks++;
                long secondsTaken = Duration.between(this.replacementStartTime, LocalDateTime.now()).getSeconds();
                long projectedSeconds = (long) ((double) secondsTaken * ((1.0 / (((double) this.finishedChunks)/totalChunks)) - 1.0));
                long projectedHours = projectedSeconds / 3600;
                long projectedMinutes = (projectedSeconds - projectedHours*3600) / 60;
                projectedSeconds = projectedSeconds - projectedHours*3600 - projectedMinutes*60;
                Strife3.LOGGER.info("Finished Z-Row: " + this.totalProgress[1] + " | Replaced: " + this.replacedSchematics + " schemactics this Z-Row" + " | " + String.valueOf((((double) this.finishedChunks)/totalChunks)) + " " + projectedHours + "hr(s) " + projectedMinutes + "min(s) " + projectedSeconds + "sec");
                this.replacedSchematics = 0;
                if(this.totalProgress[1] != schematicReplacementStartZ) {
                    for (ArrayList<ArrayList<ArrayList<SchematicReference.SchematicSearchResult>>> individualSchematic : schematicResults) {
                        /*for (int i = 0; i < 16; i++) {
                            indivdualSchematic.set(32, indivdualSchematic.getFirst());
                            for(ArrayList<SchematicReference.SchematicSearchResult> individualBlocks : indivdualSchematic.get(32)){
                                individualBlocks.clear();
                            }
                            indivdualSchematic.removeFirst();
                        }*/
                        individualSchematic.set(3, individualSchematic.getFirst());
                        individualSchematic.removeFirst();
                        for(ArrayList<SchematicReference.SchematicSearchResult> fun : individualSchematic.get(2)){
                            fun.clear();
                        }
                    }
                }
                this.totalProgress[0] = schematicReplacementStartX;
                this.totalProgress[1]++;
            }
        }else{
            this.totalProgress[0]++;
        }

        Strife3.LOGGER.info("" + this.totalProgress[0]);

        /*if(this.chunkProgress[0] == 15){
            if(this.chunkProgress[1] == 15){
                this.finishedChunks++;
                long secondsTaken = Duration.between(this.replacementStartTime, LocalDateTime.now()).getSeconds();
                long projectedSeconds = (long) ((double) secondsTaken * ((1.0 / (((double) this.finishedChunks)/totalChunks)) - 1.0));
                long projectedHours = projectedSeconds / 3600;
                long projectedMinutes = (projectedSeconds - projectedHours*3600) / 60;
                projectedSeconds = projectedSeconds - projectedHours*3600 - projectedMinutes*60;
                Strife3.LOGGER.info("Finished Chunk: " + this.totalProgress[0] + "," + this.totalProgress[1] + " | Replaced: " + this.replacedSchematics + " schemactics this chunk" + " | " + String.valueOf((((double) this.finishedChunks)/totalChunks)) + " " + projectedHours + "hr(s) " + projectedMinutes + "min(s) " + projectedSeconds + "sec");
                this.replacedSchematics = 0;
                if(this.totalProgress[0] == schematicReplacementEndX){
                    if(this.totalProgress[1] == schematicReplacementEndZ){
                        this.updateWorld = false;
                    }else{
                        this.totalProgress[0] = schematicReplacementStartX;
                        this.totalProgress[1]++;
                        if(this.totalProgress[1] != schematicReplacementStartZ + 1) {
                            for (ArrayList<ArrayList<ArrayList<SchematicReference.SchematicSearchResult>>> indivdualSchematic : schematicResults) {
                                for (int i = 0; i < 16; i++) {
                                    indivdualSchematic.set(32, indivdualSchematic.getFirst());
                                    for(ArrayList<SchematicReference.SchematicSearchResult> individualBlocks : indivdualSchematic.get(32)){
                                        individualBlocks.clear();
                                    }
                                    indivdualSchematic.removeFirst();
                                }
                            }
                        }
                    }
                }else{
                    this.totalProgress[0]++;
                }
                this.chunkProgress[0] = 0;
                this.chunkProgress[1] = 0;
            }else {
                this.chunkProgress[0] = 0;
                this.chunkProgress[1]++;
            }
        }else{
            this.chunkProgress[0]++;
        }*/
    }

    public void testSchematics(){
        if(schematics.isEmpty()){
            for (File file : schematicsPath.toFile().listFiles()) {
                NbtSizeTracker sizeTracker = new NbtSizeTracker(2097152, 500);
                try {
                    Strife3.LOGGER.info(file.getName());
                    if (replacementKeys.containsKey(file.getName())) {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys, replacementKeys.get(file.getName()),file.getName()));
                    } else {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys,file.getName()));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        SchematicReference.debug = true;
        Strife3.LOGGER.info("Check Schematic at: " + this.getBlockPos());

        ArrayList<SchematicReference.SchematicSearchResult> foundSchematics = new ArrayList<>();
        for(int j = 0; j < this.schematicSize; j++){
            SchematicReference.SchematicSearchResult currentSchematicResult = schematics.get(j).checkPositionAndSurroundings(this.getBlockPos(),this.getServerWorld(),schematicResults.get(j));
            if(currentSchematicResult.result()){
                Strife3.LOGGER.info("Found " + currentSchematicResult.schematic().name + " with confidence: " + (1 - currentSchematicResult.blockPercentage()));
                foundSchematics.add(currentSchematicResult);
            }
            Strife3.LOGGER.info("Checked Schematic " + j + "/" + this.schematicSize);
        }
        if(foundSchematics.size() == 1){
            foundSchematics.get(0).schematic().replacePosition(this.getBlockPos(),this.getServerWorld(),foundSchematics.get(0).transformation());
        }if(foundSchematics.size() > 1){
            SchematicReference.SchematicSearchResult currentMax = foundSchematics.get(0);
            for(int k = 1; k < foundSchematics.size(); k++){
                if(foundSchematics.get(k).blockPercentage() > currentMax.blockPercentage()){
                    currentMax = foundSchematics.get(k);
                }
            }

            Strife3.LOGGER.info("Choose " + currentMax.schematic().toString());

            currentMax.schematic().replacePosition(this.getBlockPos(),this.getServerWorld(),currentMax.transformation());
        }

        SchematicReference.debug = false;
    }

    public void testTransformations(int schematicNumber){
        if(schematics.isEmpty()){
            for (File file : schematicsPath.toFile().listFiles()) {
                NbtSizeTracker sizeTracker = new NbtSizeTracker(2097152, 500);
                try {
                    Strife3.LOGGER.info(file.getName());
                    if (replacementKeys.containsKey(file.getName())) {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys, replacementKeys.get(file.getName()),file.getName()));
                    } else {
                        this.schematics.add(new SchematicReference(NbtIo.readCompressed(new FileInputStream(file), sizeTracker), globalReplacementKeys,file.getName()));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        schematics.get(schematicNumber).debugTransformations(this.getBlockPos(),this.getServerWorld());
    }
}
