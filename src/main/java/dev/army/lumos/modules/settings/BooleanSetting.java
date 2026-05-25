package dev.army.lumos.modules.settings;

public class BooleanSetting extends SettingValue<Boolean> {
    public BooleanSetting(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public void toggle() {
        set(!get());
    }
}
