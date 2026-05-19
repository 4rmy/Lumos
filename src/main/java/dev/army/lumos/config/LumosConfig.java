package dev.army.lumos.config;

import dev.army.lumos.util.Color4;

@SuppressWarnings("unused")
public class LumosConfig {
    public general General = new general();
    public mining Mining = new mining();

    public static class general {
        public static class clickui extends OptionBase {
            public Color4 primary = Color4.from(0xCC7f00ff);
            public Color4 secondary = Color4.from(0xFF5000d0);
            public Color4 disabled = Color4.from(0xCC200040);
        }
        public clickui ClickUI = new clickui();
    }

    public static class mining {
        public static class commissiontracker extends OptionBase {
        }
        public commissiontracker CommissionTracker = new commissiontracker();
    }

    public static class OptionBase {
        public boolean enabled = false;
    }
}
