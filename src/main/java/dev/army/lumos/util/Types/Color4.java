package dev.army.lumos.util.Types;

public class Color4 {
    public static Color4 WHITE = Color4.from(0xFFFFFFFF);
    public static Color4 BLACK = Color4.from(0xFF000000);

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

    private static double srgb(int c) {
        double v = c / 255.0;
        return (v <= 0.03928) ? v / 12.92 : Math.pow((v + 0.055) / 1.055, 2.4);
    }

    public int toRGBA() {
        return (this.r << 24) | (this.g << 16) | (this.b << 8) | this.a;
    }

    public int toARGB() {
        return (this.a << 24) | (this.r << 16) | (this.g << 8) | this.b;
    }

    public double luminance() {
        return 0.2126 * srgb(r) + 0.7152 * srgb(g) + 0.0722 * srgb(b);
    }
}
