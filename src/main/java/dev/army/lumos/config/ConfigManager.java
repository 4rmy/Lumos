package dev.army.lumos.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.army.lumos.util.LumosLogger;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("lumos.json5");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static LumosConfig config;

    public static void load() {
        try {
            if (!Files.exists(PATH)) {
                config = new LumosConfig();
                save();
                return;
            }

            String json = Files.readString(PATH);
            config = GSON.fromJson(json, LumosConfig.class);

            if (config == null) {
                config = new LumosConfig();
            }

        } catch (Exception e) {
            LumosLogger.error("[Lumos] Failed to load config, using defaults");
            config = new LumosConfig();
        } finally {
            save();
        }
    }

    public static LumosConfig get() {
        return config;
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(config));
        } catch (Exception e) {
            LumosLogger.error("[Lumos] Failed to save config\n"+e.getMessage());
        }
    }

    public static void reset() {
        config = new LumosConfig();
        save();
    }
}