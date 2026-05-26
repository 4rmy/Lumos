package dev.army.lumos.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.settings.SettingValue;
import dev.army.lumos.util.LumosLogger;

public class ConfigSerializer {
    public static JsonObject serializeModule(ModuleBase module) {

        JsonObject object = new JsonObject();
        JsonObject settingsObject = new JsonObject();

        object.addProperty("enabled", module.isEnabled());

        for (SettingValue<?> setting : module.getSettings()) {
            settingsObject.add(setting.getName(), setting.serialize());
        }

        object.add("settings", settingsObject);

        return object;
    }

    public static void deserializeModule(ModuleBase module, JsonObject object) {

        if (object.has("enabled")) {
            module.setEnabled(object.get("enabled").getAsBoolean(), false);
        }

        if (!object.has("settings")) {
            return;
        }

        JsonObject settingsObject = object.getAsJsonObject("settings");

        for (SettingValue<?> setting : module.getSettings()) {

            if (!settingsObject.has(setting.getName())) {
                continue;
            }

            JsonElement element = settingsObject.get(setting.getName());

            deserializeSetting(setting, element);
        }
    }

    private static void deserializeSetting(SettingValue<?> setting, JsonElement element) {

        try {
            setting.deserialize(element);
        } catch (Exception e) {
            LumosLogger.error("Failed to deserialize setting: " + setting.getName(), e);
        }
    }
}