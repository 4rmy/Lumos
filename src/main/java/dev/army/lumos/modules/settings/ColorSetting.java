package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.army.lumos.util.types.Color;

public class ColorSetting extends SettingValue<Color> {

    public ColorSetting(String name, Color defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JsonElement serialize() {

        JsonObject object = new JsonObject();

        object.addProperty("r", get().getRed());
        object.addProperty("g", get().getGreen());
        object.addProperty("b", get().getBlue());
        object.addProperty("a", get().getAlpha());

        return object;
    }

    @Override
    public void deserialize(JsonElement element) {

        JsonObject object = element.getAsJsonObject();

        int r = object.has("r") ? object.get("r").getAsInt() : 255;
        int g = object.has("g") ? object.get("g").getAsInt() : 255;
        int b = object.has("b") ? object.get("b").getAsInt() : 255;
        int a = object.has("a") ? object.get("a").getAsInt() : 255;

        set(new Color(r, g, b, a));
    }
}