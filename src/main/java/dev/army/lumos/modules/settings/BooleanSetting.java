package dev.army.lumos.modules.settings;

public abstract class BooleanSetting {
    boolean value;

    public void toggle() {
        this.value = !this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    abstract void onEnable();

    abstract void onDisable();
}
