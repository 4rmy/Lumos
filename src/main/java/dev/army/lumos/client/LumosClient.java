package dev.army.lumos.client;

import dev.army.lumos.command.LumosCommands;
import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.util.LumosLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.jetbrains.annotations.Contract;

public class LumosClient implements ClientModInitializer {
    // Mod Constants
    private static final String ModName = "Lumos";
    private static final String ModId = "lumos";
    private static final String ModVersion = "1.0.0";

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModName() {
        return LumosClient.ModName;
    }

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModId() {
        return LumosClient.ModId;
    }

    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static String getModVersion() {
        return LumosClient.ModVersion;
    }


    @Override
    public void onInitializeClient() {
        // Client setup
        ConfigManager.load(); // load current config
        ClientLifecycleEvents.CLIENT_STOPPING.register(minecraftClient -> {
            ConfigManager.save();
        }); // save config when you quit

        // Command Setup
        CommandRegistrationCallback.EVENT.register((
                dispatcher,
                registryAccess,
                environment) -> {
            LumosCommands.register(dispatcher);
        });

        LumosLogger.info("Mod Initialized.");
    }
}
