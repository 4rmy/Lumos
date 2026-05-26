package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;

import java.util.function.Consumer;

public abstract class SettingValue<T> {
    private final String name;
    private T value;
    private Consumer<T> callback = null;

    public SettingValue(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        boolean changed = !java.util.Objects.equals(this.value, value);

        this.value = value;

        if (changed && callback != null) {
            callback.accept(this.value);
        }
    }

    public void onChanged(Consumer<T> callback) {
        this.callback = callback;
    }

    public abstract JsonElement serialize();

    public abstract void deserialize(JsonElement element);
}
