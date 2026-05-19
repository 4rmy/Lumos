package dev.army.lumos.ui.clickui.widgits;

import net.minecraft.client.gui.DrawContext;

public class ColorWidgit extends Widgit {
    public ColorWidgit(int x, int y) {
        super(x, y);
    }

    @Override
    int getHeight() {
        return Widgit.minHeight;
    }

    @Override
    void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }
}
