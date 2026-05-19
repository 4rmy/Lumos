package dev.army.lumos.ui.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class Widgit {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    public static final int width = 100;
    public static final int minHeight = 13; // font height + padding = 9 + 4
    protected int x = 0;
    protected int y = 0;

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public abstract int getHeight();
    public abstract void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks, int x, int y);
}
