package dev.army.lumos.config.clickui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;

public class LumosContext extends DrawContext {
    public LumosContext(DrawContext context, int mouseX, int mouseY) {
        this(MinecraftClient.getInstance(), context.state, mouseX, mouseY);
    }

    public LumosContext(MinecraftClient client, GuiRenderState state, int mouseX, int mouseY) {
        super(client, state, mouseX, mouseY);
    }

    /* flags:
        0001 - top left
        0010 - top right
        0100 - bottom left
        1000 - bottom right
    */
    public void fillRounded(int x1, int y1, int x2, int y2, int color, int flags) {
        // top
        this.fill(((flags & 0b1) != 0) ? x1 + 1 : x1, y1, ((flags & 0b10) != 0) ? x2 - 1 : x2, y1 + 1, color);
        // middle
        this.fill(x1, y1 + 1, x2, y2 - 1, color);
        // bottom
        this.fill(((flags & 0b100) != 0) ? x1 + 1 : x1, y2 - 1, ((flags & 0b1000) != 0) ? x2 - 1 : x2, y2, color);
    }
}
