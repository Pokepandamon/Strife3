package net.pokepandamon.strife3.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pokepandamon.strife3.Strife3;

public record AdminBooleanRequestC2SPayload(boolean adminValue) implements CustomPayload {
    public static final CustomPayload.Id<AdminBooleanRequestC2SPayload> ID = new CustomPayload.Id<>(Strife3.ADMIN_VALUE_PACKET);
    public static final PacketCodec<RegistryByteBuf, AdminBooleanRequestC2SPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, AdminBooleanRequestC2SPayload::adminValue, AdminBooleanRequestC2SPayload::new);
    // should you need to send more data, add the appropriate record parameters and change your codec:
    // public static final PacketCodec<RegistryByteBuf, BlockHighlightPayload> CODEC = PacketCodec.tuple(
    //         BlockPos.PACKET_CODEC, BlockHighlightPayload::blockPos,
    //         PacketCodecs.INTEGER, BlockHighlightPayload::myInt,
    //         Uuids.PACKET_CODEC, BlockHighlightPayload::myUuid,
    //         BlockHighlightPayload::new
    // );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}