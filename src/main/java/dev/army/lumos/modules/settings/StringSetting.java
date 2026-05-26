package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringSetting extends SettingValue<String> {

    private final int maxLength;

    public StringSetting(String name, String defaultValue, int maxLength) {
        super(name, defaultValue);

        this.maxLength = maxLength;
    }

    @Override
    public void set(String value) {

        if (value == null) {
            value = "";
        }

        if (value.length() > maxLength) {
            value = value.substring(0, maxLength);
        }

        super.set(value);
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(get());
    }

    @Override
    public void deserialize(JsonElement element) {
        set(element.getAsString());
    }
}