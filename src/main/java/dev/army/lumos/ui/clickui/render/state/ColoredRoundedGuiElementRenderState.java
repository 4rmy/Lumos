package dev.army.lumos.ui.clickui.render.state;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record ColoredRoundedGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose,
                                                  int x0, int y0, int x1, int y1, int radius, int col1, int col2,
                                                  @Nullable ScreenRect scissorArea,
                                                  @Nullable ScreenRect bounds) implements SimpleGuiElementRenderState {

    public ColoredRoundedGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, int x0, int y0, int x1, int y1, int radius, int col1, int col2, @Nullable ScreenRect scissorArea) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, radius, col1, col2, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    private static @Nullable ScreenRect createBounds(int x0, int y0, int x1, int y1, Matrix3x2fc pose, @Nullable ScreenRect scissorArea) {
        ScreenRect screenRect = (new ScreenRect(x0, y0, x1 - x0, y1 - y0)).transformEachVertex(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    @Override
    public void setupVertices(VertexConsumer vertices) {
        float x0 = this.x0();
        float y0 = this.y0();
        float x1 = this.x1();
        float y1 = this.y1();

        float r = Math.min(this.radius(), Math.min((x1 - x0) * 0.5f, (y1 - y0) * 0.5f));

        int segments = 12;

        // top-left
        emitCorner(vertices, x0 + r, y0 + r, r, 180.0f, 270.0f, segments);

        // top-right
        emitCorner(vertices, x1 - r, y0 + r, r, 270.0f, 360.0f, segments);

        // bottom-right
        emitCorner(vertices, x1 - r, y1 - r, r, 0.0f, 90.0f, segments);

        // bottom-left
        emitCorner(vertices, x0 + r, y1 - r, r, 90.0f, 180.0f, segments);

        // close strip
        float angle = (float) Math.toRadians(180.0f);

        vertices.vertex(this.pose(), x0, y0 + r).color(this.col1());

        vertices.vertex(this.pose(), x0 + r, y0 + r).color(this.col1());
    }

    private void emitCorner(VertexConsumer vertices, float cx, float cy, float radius, float startDeg, float endDeg, int segments) {
        float step = (endDeg - startDeg) / segments;

        for (int i = 0; i <= segments; i++) {
            float angle = (float) Math.toRadians(startDeg + step * i);

            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            // OUTER perimeter vertex
            vertices.vertex(this.pose(), cx + cos * radius, cy + sin * radius).color(this.col1());

            // INNER body vertex
            vertices.vertex(this.pose(), cx, cy).color(this.col1());
        }
    }
}
