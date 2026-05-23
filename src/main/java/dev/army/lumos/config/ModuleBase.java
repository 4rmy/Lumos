package dev.army.lumos.config;

import dev.army.lumos.util.LumosModuleReflector;

public class ModuleBase extends LumosModuleReflector {
    public boolean enabled = false;

    public void toggle() {
        this.enabled = !this.enabled;
    }
}
