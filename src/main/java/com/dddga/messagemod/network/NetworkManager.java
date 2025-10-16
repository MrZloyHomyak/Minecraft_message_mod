package com.dddga.messagemod.network;

import com.dddga.messagemod.database.DatabaseManager;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import com.dddga.messagemod.MessageOuterClass;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;


public class NetworkManager {
    public static final CustomPayload.Id<MessagePayload> MESSAGE_PACKET_ID =
            new CustomPayload.Id<>(Identifier.of("messagemod", "send_message"));
    private static boolean serverInitialized = false;

    public record MessagePayload(byte[] messageData) implements CustomPayload {
        @Override
        public Id<? extends  CustomPayload> getId() {
            return MESSAGE_PACKET_ID;
        }

        public static final PacketCodec<PacketByteBuf, MessagePayload> CODEC =
                PacketCodec.of((payload, buf) -> {
                    buf.writeByteArray(payload.messageData);
                }, buf -> {
                    byte[] data = buf.readByteArray();
                    return new MessagePayload(data);
                });
    }

    public static void initializeClient() {
        System.out.println("NetworkManager client setup complete.");
    }

    public static void initializeServer() {
        if (serverInitialized) {
            return;
        }

        PayloadTypeRegistry.playC2S().register(MESSAGE_PACKET_ID, MessagePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MESSAGE_PACKET_ID,
                (payload, context) -> {
                    try {
                        byte[] messageData = payload.messageData();
                        MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(messageData);
                        String text = message.getText();
                        UUID playerUuid = context.player().getUuid();

                        System.out.println("Network success");
                        String playerName = context.player().getGameProfile().getName();
                        System.out.println("Received message from " + playerName + ": " + text);

                        context.server().execute(() -> {
                            DatabaseManager.saveMessage(playerUuid, text);
                        });
                    } catch (Exception e) {
                        System.err.println("Error processing message: " + e.getMessage());
                    }
                });
        serverInitialized = true;
        System.out.println("NetworkManager server setup complete.");
    }
}
