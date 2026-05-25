package dev.army.lumos.modules;

import dev.army.lumos.modules.settings.BooleanSetting;
import dev.army.lumos.modules.settings.SettingValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ModuleBase {
    private final List<Object> settings = new ArrayList<>();
    private final BooleanSetting enabled = new BooleanSetting("enabled", false);

    public ModuleBase() {
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(this);

                if (value instanceof SettingValue) {
                    settings.add(value);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled.get() == enabled) return;

        this.enabled.set(enabled);

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        enabled.toggle();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public List<Object> getSettings() {
        return settings;
    }
}
