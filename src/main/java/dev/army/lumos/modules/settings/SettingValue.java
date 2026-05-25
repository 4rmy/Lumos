package dev.army.lumos.modules.settings;

import dev.army.lumos.config.ConfigManager;

public class SettingValue<T> {
    private final String name;
    private T value;

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
        this.value = value;
    }
}
