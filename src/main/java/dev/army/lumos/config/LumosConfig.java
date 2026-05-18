package dev.army.lumos.config;

import dev.army.lumos.util.Color4;

@SuppressWarnings("unused")
public class LumosConfig {
    public General general = new General();

    public static class General {
        public boolean enabled = false;
        public Color4 primary = Color4.from(0xCC917293);
        public Color4 secondary = Color4.from(0xFFF9F8F9);
        public Color4 disabled = Color4.from(0xCCF9F8F9);
    }

    public Testing testing = new Testing();
    public static class Testing {
        public boolean enabled = false;
    }
}
