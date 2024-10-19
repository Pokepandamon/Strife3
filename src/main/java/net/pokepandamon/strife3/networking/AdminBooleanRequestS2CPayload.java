package net.pokepandamon.strife3.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pokepandamon.strife3.Strife3;

public record AdminBooleanRequestS2CPayload(boolean adminValue) implements CustomPayload {
    public static final Id<AdminBooleanRequestS2CPayload> ID = new Id<>(Strife3.ADMIN_VALUE_PACKET);
    public static final PacketCodec<RegistryByteBuf, AdminBooleanRequestS2CPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, AdminBooleanRequestS2CPayload::adminValue, AdminBooleanRequestS2CPayload::new);
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