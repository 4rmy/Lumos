package dev.army.lumos.config;

import dev.army.lumos.util.ConfigTypes.Color4;

@SuppressWarnings("unused")
public class LumosConfig {

    public final General general = new General();
    public final Mining mining = new Mining();

    public static class General {
        public final ClickUI clickUI = new ClickUI();
    }

    public static class ClickUI {
        public boolean enabled = true;
        public Color4 primary = Color4.from(0xCC7f00ff);
        public Color4 secondary = Color4.from(0xFF5000d0);
        public Color4 disabled = Color4.from(0xCC200040);
    }

    public static class Mining {
        public final CommissionTracker commissionTracker = new CommissionTracker();
    }

    public static class CommissionTracker {
        public boolean enabled = false;
    }
}
