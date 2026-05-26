package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumSetting<T extends Enum<T>> extends SettingValue<T> {

    private final T[] values;
    private final Class<T> enumClass;

    public EnumSetting(String name, T defaultValue, T[] values) {
        super(name, defaultValue);

        this.values = values;
        this.enumClass = defaultValue.getDeclaringClass();
    }

    public void next() {

        int current = get().ordinal();
        current++;

        if (current >= values.length) {
            current = 0;
        }

        set(values[current]);
    }

    public T[] getValues() {
        return values;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(get().name());
    }

    @Override
    public void deserialize(JsonElement element) {

        try {
            set(Enum.valueOf(enumClass, element.getAsString()));
        } catch (Exception ignored) {
            set(values[0]);
        }
    }
}