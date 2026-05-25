package dev.army.lumos.modules;

import dev.army.lumos.modules.settings.SettingValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ModuleBase {
    private final List<Object> settings = new ArrayList<>();
    private boolean enabled = false;

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
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public List<Object> getSettings() {
        return settings;
    }
}
