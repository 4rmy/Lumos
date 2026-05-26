package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class KeybindSetting extends SettingValue<Integer> {

    public KeybindSetting(String name, int defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(get());
    }

    @Override
    public void deserialize(JsonElement element) {
        set(element.getAsInt());
    }
}