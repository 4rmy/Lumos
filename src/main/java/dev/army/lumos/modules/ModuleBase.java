package dev.army.lumos.modules;

import dev.army.lumos.modules.settings.SettingValue;

import java.util.ArrayList;
import java.util.List;

public abstract class ModuleBase {
    private final List<SettingValue<?>> settings = new ArrayList<>();
    private final Module metadata;
    private boolean enabled = false;

    public ModuleBase() {
        metadata = getClass().getAnnotation(Module.class);
        ModuleManager.register(this);
    }

    public Module getMetadata() {
        return metadata;
    }

    protected <T extends SettingValue<?>> T register(T setting) {
        settings.add(setting);
        return setting;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public void setEnabled(boolean enabled, boolean callEvents) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;
        if (callEvents) {
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public List<SettingValue<?>> getSettings() {
        return settings;
    }
}
