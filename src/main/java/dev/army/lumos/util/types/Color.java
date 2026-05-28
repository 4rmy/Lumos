package dev.army.lumos.util.types;

@SuppressWarnings("unused")
public class Color {
    private float h;
    private float s;
    private float v;
    private float a;

    /*
     * RGB constructor
     * Accepts:
     * 0xRRGGBB
     * or
     * 0xAARRGGBB
     */
    public Color(int rgb) {
        this(((rgb >> 16) & 0xFF) / 255.0f, ((rgb >> 8) & 0xFF) / 255.0f, (rgb & 0xFF) / 255.0f, ((rgb >> 24) & 0xFF) / 255.0f);
    }

    /*
     * RGB float constructor
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }

    /*
     * RGBA float constructor
     */
    public Color(float r, float g, float b, float a) {
        this.a = clamp(a);

        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));

        float delta = max - min;

        // Hue
        if (delta == 0) {
            h = 0;
        } else if (max == r) {
            h = 60 * (((g - b) / delta) % 6);
        } else if (max == g) {
            h = 60 * (((b - r) / delta) + 2);
        } else {
            h = 60 * (((r - g) / delta) + 4);
        }

        if (h < 0) h += 360;

        // Saturation
        s = max == 0 ? 0 : delta / max;

        // Value
        v = max;
    }

    public float getHue() {
        return h;
    }

    public void setHue(float h) {
        this.h = h;
    }

    public float getSaturation() {
        return s;
    }

    public void setSaturation(float s) {
        this.s = clamp(s);
    }

    public float getValue() {
        return v;
    }

    public void setValue(float v) {
        this.v = clamp(v);
    }

    public float getAlpha() {
        return a;
    }

    public void setAlpha(float a) {
        this.a = clamp(a);
    }

    /*
     * HSV -> RGB
     */
    public int getRed() {
        return (int) (toRGB()[0] * 255);
    }

    public int getGreen() {
        return (int) (toRGB()[1] * 255);
    }

    public int getBlue() {
        return (int) (toRGB()[2] * 255);
    }

    public int getAlphaInt() {
        return (int) (a * 255);
    }

    /*
     * Returns:
     * 0xAARRGGBB
     */
    public int getARGB() {
        return (getAlphaInt() << 24) | (getRed() << 16) | (getGreen() << 8) | getBlue();
    }

    /*
     * Returns:
     * 0xRRGGBB
     */
    public int getRGB() {
        return (getRed() << 16) | (getGreen() << 8) | getBlue();
    }

    private float[] toRGB() {

        float c = v * s;
        float x = c * (1 - Math.abs((h / 60f) % 2 - 1));
        float m = v - c;

        float r = 0;
        float g = 0;
        float b = 0;

        if (h < 60) {
            r = c;
            g = x;
        } else if (h < 120) {
            r = x;
            g = c;
        } else if (h < 180) {
            g = c;
            b = x;
        } else if (h < 240) {
            g = x;
            b = c;
        } else if (h < 300) {
            r = x;
            b = c;
        } else {
            r = c;
            b = x;
        }

        return new float[]{r + m, g + m, b + m};
    }

    private float clamp(float v) {
        return Math.clamp(v, 0f, 1f);
    }
}