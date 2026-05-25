package dev.army.lumos.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.settings.BooleanSetting;
import dev.army.lumos.modules.settings.ColorSetting;
import dev.army.lumos.modules.settings.EnumSetting;
import dev.army.lumos.modules.settings.SettingValue;
import dev.army.lumos.util.types.Color;

public class ConfigSerializer {

    public static JsonObject serializeModule(ModuleBase module) {

        JsonObject object = new JsonObject();
        JsonObject settingsObject = new JsonObject();

        for (Object obj : module.getSettings()) {

            if (!(obj instanceof SettingValue<?> setting)) continue;

            JsonElement element = serializeSetting(setting);

            if (element != null) {
                settingsObject.add(setting.getName(), element);
            }
        }

        object.add("settings", settingsObject);

        return object;
    }

    public static void deserializeModule(ModuleBase module, JsonObject object) {

        if (object.has("enabled")) {
            module.setEnabled(object.get("enabled").getAsBoolean());
        }

        if (!object.has("settings")) return;

        JsonObject settingsObject = object.getAsJsonObject("settings");

        for (Object obj : module.getSettings()) {

            if (!(obj instanceof SettingValue<?> setting)) continue;

            if (!settingsObject.has(setting.getName())) continue;

            JsonElement element = settingsObject.get(setting.getName());

            deserializeSetting(setting, element);
        }
    }

    private static JsonElement serializeSetting(SettingValue<?> setting) {

        JsonObject object = new JsonObject();

        if (setting instanceof BooleanSetting bool) {
            return primitive(bool.get());
        }

        if (setting instanceof ColorSetting colorSetting) {

            Color color = colorSetting.get();

            return primitive(color.getARGB());
        }

        if (setting instanceof EnumSetting<?> enumSetting) {
            return primitive(enumSetting.get().name());
        }

        Object value = setting.get();

        if (value instanceof Number number) {
            return primitive(number);
        }

        if (value instanceof String string) {
            return primitive(string);
        }

        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void deserializeSetting(SettingValue setting, JsonElement element) {

        try {

            if (setting instanceof BooleanSetting) {

                setting.set(element.getAsBoolean());

                return;
            }

            if (setting instanceof ColorSetting) {

                setting.set(new Color(element.getAsInt()));

                return;
            }

            if (setting instanceof EnumSetting enumSetting) {

                Enum current = (Enum) enumSetting.get();

                Class<? extends Enum> enumClass = current.getDeclaringClass();

                Enum value = Enum.valueOf(enumClass, element.getAsString());

                enumSetting.set(value);

                return;
            }

            Object currentValue = setting.get();

            if (currentValue instanceof Integer) {
                setting.set(element.getAsInt());
            } else if (currentValue instanceof Float) {
                setting.set(element.getAsFloat());
            } else if (currentValue instanceof Double) {
                setting.set(element.getAsDouble());
            } else if (currentValue instanceof Long) {
                setting.set(element.getAsLong());
            } else if (currentValue instanceof String) {
                setting.set(element.getAsString());
            }

        } catch (Exception ignored) {
        }
    }

    private static JsonElement primitive(Boolean value) {
        return new com.google.gson.JsonPrimitive(value);
    }

    private static JsonElement primitive(Number value) {
        return new com.google.gson.JsonPrimitive(value);
    }

    private static JsonElement primitive(String value) {
        return new com.google.gson.JsonPrimitive(value);
    }
}