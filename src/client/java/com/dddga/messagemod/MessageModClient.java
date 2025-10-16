package com.dddga.messagemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.dddga.messagemod.network.NetworkManager;
import net.minecraft.client.MinecraftClient;
import com.dddga.messagemod.gui.TextInputScreen;

public class MessageModClient implements ClientModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        System.out.println("Message Mod Client initialized!");

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.messagemod.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.messagemod.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                client.setScreen(new TextInputScreen());
            }
        });
    }
}