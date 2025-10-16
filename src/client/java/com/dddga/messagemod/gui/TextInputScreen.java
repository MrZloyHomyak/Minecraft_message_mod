package com.dddga.messagemod.gui;

import com.dddga.messagemod.MessageOuterClass;
import com.dddga.messagemod.network.NetworkManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;


public class TextInputScreen extends Screen {
    private TextFieldWidget textField;

    public TextInputScreen() {
        super(Text.literal("Message Sender"));
    }

    @Override
    protected void init() {
        super.init();

        this.textField = new TextFieldWidget(
                this.textRenderer,
                this.width / 2 - 100,
                this.height / 2 - 20,
                200, 20,
                Text.literal("Enter message")
        );
        this.addDrawableChild(this.textField);

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Send"),
                button -> this.sendMessage()
        ).position(
                this.width / 2 - 50,
                this.height / 2 + 10
        ).size(100, 20).build());
    }

    private void sendMessage() {
        String messageText = this.textField.getText();
        if (!messageText.isEmpty()) {
            try {
                MessageOuterClass.Message message =
                        MessageOuterClass.Message.newBuilder()
                                .setText(messageText)
                                .build();

                byte[] messageBytes = message.toByteArray();

                if (client.getNetworkHandler() != null) {
                    NetworkManager.MessagePayload payload = new NetworkManager.MessagePayload(messageBytes);

                    ClientPlayNetworking.send(payload);
                    System.out.println("Message sent to server: " + messageText);
                }

                this.close();
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        }
    }

    @Override
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        context.fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                "Send Message to Server",
                this.width / 2,
                this.height / 2 - 50,
                0xFFFFFF);
    }
}