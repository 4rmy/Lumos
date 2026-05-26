package dev.army.lumos.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.ModuleManager;
import dev.army.lumos.util.LumosLogger;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Path CONFIG_PATH = Path.of("config/lumos/config.json");

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            JsonObject root = new JsonObject();
            JsonObject modulesObject = new JsonObject();

            root.addProperty("version", LumosClient.getModVersion());

            for (ModuleBase module : ModuleManager.getModules()) {

                JsonObject moduleObject = ConfigSerializer.serializeModule(module);

                modulesObject.add(module.getClass().getSimpleName(), moduleObject);
            }

            root.add("modules", modulesObject);

            Files.writeString(CONFIG_PATH, GSON.toJson(root));

        } catch (Exception e) {
            LumosLogger.error("Failed to save config!", e);
        }
    }

    public static void load() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                save();
                return;
            }

            String json = Files.readString(CONFIG_PATH);

            JsonObject root = JsonParser.parseString(json).getAsJsonObject();

            JsonObject modulesObject = root.getAsJsonObject("modules");

            for (ModuleBase module : ModuleManager.getModules()) {

                String name = module.getClass().getSimpleName();

                if (!modulesObject.has(name)) continue;

                JsonObject moduleObject = modulesObject.getAsJsonObject(name);

                ConfigSerializer.deserializeModule(module, moduleObject);
            }

        } catch (Exception e) {
            LumosLogger.error("Failed to read config, using defaults!", e);
        }
    }
}