package dev.army.lumos.ui.clickui.widgits;

import net.minecraft.client.gui.DrawContext;

public class SliderWidgit extends Widgit {
    public SliderWidgit(int x, int y) {
        super(x, y);
    }

    @Override
    int getHeight() {
        return minHeight;
    }

    @Override
    void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }
}
