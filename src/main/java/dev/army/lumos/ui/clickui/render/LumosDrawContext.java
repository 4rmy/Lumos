package dev.army.lumos.ui.clickui.render;

import dev.army.lumos.ui.clickui.render.state.ColoredRoundedGuiElementRenderState;
import dev.army.lumos.util.LumosPipelines;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.texture.TextureSetup;
import org.joml.Matrix3x2f;

public class LumosDrawContext extends DrawContext {
    public LumosDrawContext(DrawContext dc, int mouseX, int mouseY) {
        this(MinecraftClient.getInstance(), dc.state, mouseX, mouseY);
    }

    public LumosDrawContext(MinecraftClient client, GuiRenderState state, int mouseX, int mouseY) {
        super(client, state, mouseX, mouseY);
    }

    public void fillRounded(int x1, int y1, int x2, int y2, int color, int radius) {
        int tx1 = Math.min(x1, x2);
        int tx2 = Math.max(x1, x2);
        int ty1 = Math.min(y1, y2);
        int ty2 = Math.max(y1, y2);

        this.state.addSimpleElement(new ColoredRoundedGuiElementRenderState(LumosPipelines.GUI_STRIP, TextureSetup.empty(), new Matrix3x2f(this.getMatrices()), tx1, ty1, tx2, ty2, radius, color, color, this.scissorStack.peekLast()));
        this.fill(tx1 + radius, ty1 + radius, tx2 - radius, ty2 - radius, color);
    }
}
