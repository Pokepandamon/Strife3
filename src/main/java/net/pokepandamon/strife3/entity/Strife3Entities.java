package net.pokepandamon.strife3.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.entity.custom.BlockEntity;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;

public class Strife3Entities {
    public static final EntityType<GreaterVerluerEntity> GREATER_VERLUER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Strife3.MOD_ID, "greater_verluer"), FabricEntityTypeBuilder.create(SpawnGroup.UNDERGROUND_WATER_CREATURE, GreaterVerluerEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).build());
    public static final EntityType<BlockEntity> BLOCK_ENTITY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Strife3.MOD_ID, "block_entity"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, BlockEntity::new).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeBlocks(64).build());

    public static void registerStrife3Entities(){
        Strife3.LOGGER.info("Registering Entities for " + Strife3.MOD_ID);
    }
}
