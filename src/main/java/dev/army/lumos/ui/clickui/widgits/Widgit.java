package dev.army.lumos.ui.clickui.widgits;

import net.minecraft.client.gui.DrawContext;

public abstract class Widgit {
    private static final int width = 100;
    public static final int minHeight = 13; // padding + font height = 4 + 9

    protected int x;
    protected int y;

    public Widgit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public int getWidth() { return Widgit.width; }
    abstract int getHeight();

    abstract void render(DrawContext context, int mouseX, int mouseY, float deltaTicks);
}
