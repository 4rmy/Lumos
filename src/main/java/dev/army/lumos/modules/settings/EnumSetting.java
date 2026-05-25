package dev.army.lumos.modules.settings;

public class EnumSetting<T extends Enum<T>> extends SettingValue<T> {

    private final T[] values;

    public EnumSetting(String name, T defaultValue, T[] values) {
        super(name, defaultValue);
        this.values = values;
    }

    public void next() {
        int current = get().ordinal();
        current++;

        if (current >= values.length) current = 0;

        set(values[current]);
    }

    public T[] getValues() {
        return values;
    }
}
