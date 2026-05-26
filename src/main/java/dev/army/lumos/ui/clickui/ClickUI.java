package dev.army.lumos.ui.clickui;

import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import net.minecraft.client.gui.Click;

public class ClickUI {
    public static ClickUI INSTANCE = null;

    public ClickUI() {
    }

    public void render(LumosDrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        return true;
    }
}
