package dev.army.lumos.render;

import dev.army.lumos.render.state.ColoredRoundedBorderGuiElementRenderState;
import dev.army.lumos.render.state.ColoredRoundedGuiElementRenderState;
import dev.army.lumos.util.LumosPipelines;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.texture.TextureSetup;
import org.joml.Matrix3x2f;

import java.util.EnumSet;

public class LumosDrawContext extends DrawContext {
    private final int mouseX;
    private final int mouseY;
    private float deltaTicks;

    public LumosDrawContext(DrawContext dc, int mouseX, int mouseY, float deltaTicks) {
        this(MinecraftClient.getInstance(), dc.state, mouseX, mouseY, deltaTicks);
    }

    public LumosDrawContext(MinecraftClient client, GuiRenderState state, int mouseX, int mouseY, float deltaTicks) {
        super(client, state, mouseX, mouseY);
        this.deltaTicks = deltaTicks;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public void setDeltaTicks(float deltaTicks) {
        this.deltaTicks = deltaTicks;
    }

    public void fillRounded(int x1, int y1, int x2, int y2, int color1, int color2, int radius, boolean fillInside, EnumSet<RoundedCorner> corners) {
        int tx1 = Math.min(x1, x2);
        int tx2 = Math.max(x1, x2);
        int ty1 = Math.min(y1, y2);
        int ty2 = Math.max(y1, y2);

        this.state.addSimpleElement(new ColoredRoundedGuiElementRenderState(LumosPipelines.GUI_TRIANGLES, TextureSetup.empty(), new Matrix3x2f(this.getMatrices()), tx1, ty1, tx2, ty2, radius, color1, color2, fillInside, corners, this.scissorStack.peekLast()));
    }

    public void fillRounded(int x1, int y1, int x2, int y2, int color1, int color2, int radius, boolean fillInside) {
        int tx1 = Math.min(x1, x2);
        int tx2 = Math.max(x1, x2);
        int ty1 = Math.min(y1, y2);
        int ty2 = Math.max(y1, y2);

        this.state.addSimpleElement(new ColoredRoundedGuiElementRenderState(LumosPipelines.GUI_TRIANGLES, TextureSetup.empty(), new Matrix3x2f(this.getMatrices()), tx1, ty1, tx2, ty2, radius, color1, color2, fillInside, EnumSet.allOf(RoundedCorner.class), this.scissorStack.peekLast()));
    }

    public void fillRounded(int x1, int y1, int x2, int y2, int color1, int color2, int radius) {
        fillRounded(x1, y1, x2, y2, color1, color2, radius, true);
    }

    public void fillRounded(int x1, int y1, int x2, int y2, int color, int radius) {
        fillRounded(x1, y1, x2, y2, color, color, radius);
    }

    public void drawRoundedBorder(int x1, int y1, int x2, int y2, int innerColor, int outerColor, int radius, int width, EnumSet<RoundedCorner> corners) {

        int tx1 = Math.min(x1, x2);
        int tx2 = Math.max(x1, x2);

        int ty1 = Math.min(y1, y2);
        int ty2 = Math.max(y1, y2);

        this.state.addSimpleElement(new ColoredRoundedBorderGuiElementRenderState(LumosPipelines.GUI_STRIP, TextureSetup.empty(), new Matrix3x2f(this.getMatrices()), tx1, ty1, tx2, ty2, radius, width, innerColor, outerColor, corners, this.scissorStack.peekLast()));
    }
}
