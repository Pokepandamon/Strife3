package net.pokepandamon.strife3.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pokepandamon.strife3.Strife3;

public record SuperDrugRandomValueS2CPayload(int superDrugRandomValue) implements CustomPayload {
    public static final Id<SuperDrugRandomValueS2CPayload> ID = new Id<>(Strife3.SUPER_DRUG_RANDOM_VALUE);
    public static final PacketCodec<RegistryByteBuf, SuperDrugRandomValueS2CPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, SuperDrugRandomValueS2CPayload::superDrugRandomValue, SuperDrugRandomValueS2CPayload::new);
    // should you need to send more data, add the appropriate record parameters and change your codec:
    // public static final PacketCodec<RegistryByteBuf, BlockHighlightPayload> CODEC = PacketCodec.tuple(
    //         BlockPos.PACKET_CODEC, BlockHighlightPayload::blockPos,
    //         PacketCodecs.INTEGER, BlockHighlightPayload::myInt,
    //         Uuids.PACKET_CODEC, BlockHighlightPayload::myUuid,
    //         BlockHighlightPayload::new
    // );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}