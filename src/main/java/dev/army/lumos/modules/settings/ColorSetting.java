package dev.army.lumos.modules.settings;

import dev.army.lumos.util.types.Color;

public class ColorSetting extends SettingValue<Color> {
    public ColorSetting(String name, Color defaultValue) {
        super(name, defaultValue);
    }
}
