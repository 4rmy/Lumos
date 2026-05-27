package dev.army.lumos.client;

import dev.army.lumos.command.LumosCommands;
import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.hud.HudRenderer;
import dev.army.lumos.modules.ModuleManager;
import dev.army.lumos.ui.ClickUiScreen;
import dev.army.lumos.util.LumosLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;

public class LumosClient implements ClientModInitializer {
    // Mod Constants
    private static final String MOD_NAME = "Lumos";
    private static final String MOD_ID = "lumos";
    private static final String MOD_VERSION = "1.0.0";

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModName() {
        return LumosClient.MOD_NAME;
    }

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModId() {
        return LumosClient.MOD_ID;
    }

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModVersion() {
        return LumosClient.MOD_VERSION;
    }

    @Override
    public void onInitializeClient() {
        // registers Modules
        ModuleManager.load();

        // Client setup
        ConfigManager.load();
        ClientLifecycleEvents.CLIENT_STOPPING.register((listener) -> {
            ConfigManager.save();
        });

        // Command Setup
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> LumosCommands.register(dispatcher));

        // Hud Setup
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(LumosClient.getModId(), "before_chat"), HudRenderer::render);

        // GUI Setup
        ClickUiScreen.INSTANCE = new ClickUiScreen();

        LumosLogger.info("Mod Initialized.");
    }
}