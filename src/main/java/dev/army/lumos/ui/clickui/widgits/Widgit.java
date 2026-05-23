package dev.army.lumos.ui.clickui.widgits;

import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import net.minecraft.client.gui.Click;

public abstract class Widgit {
    public static final int width = 100;
    public static final int minHeight = 13; // padding + font height = 4 + 9
    public final String path;
    protected int x;
    protected int y;

    public Widgit(int x, int y, String path) {
        this.x = x;
        this.y = y;
        this.path = path;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getPath() {
        return this.path;
    }

    public int getWidth() {
        return Widgit.width;
    }

    abstract int getHeight();

    public abstract void render(LumosDrawContext ctx, int mouseX, int mouseY, float deltaTicks);

    public abstract void mouseClicked(Click click, boolean doubled);
}
