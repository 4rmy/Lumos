package dev.army.lumos.config;

import java.util.HashMap;
import java.util.Map;

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

    public static class Color4 {
        public static Color4 WHITE = Color4.from(0xFFFFFFFF);
        public int r, g, b, a;

        public Color4(int a, int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public static Color4 from(int argb) {
            return new Color4((argb >> 24) & 0xFF, (argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF);
        }

        public int toRGBA() {
            return (this.r << 24) | (this.g << 16) | (this.b << 8) | this.a;
        }

        public int toARGB() {
            return (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
        }
    }
}
