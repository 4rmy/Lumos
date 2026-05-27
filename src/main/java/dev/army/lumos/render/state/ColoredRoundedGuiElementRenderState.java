package dev.army.lumos.render.state;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.army.lumos.render.RoundedCorner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

@Environment(EnvType.CLIENT)
public record ColoredRoundedGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose,
                                                  int x0, int y0, int x1, int y1, int radius, int col1, int col2,
                                                  boolean fillInside, EnumSet<RoundedCorner> roundedCorners,
                                                  @Nullable ScreenRect scissorArea,
                                                  @Nullable ScreenRect bounds) implements SimpleGuiElementRenderState {

    public ColoredRoundedGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, int x0, int y0, int x1, int y1, int radius, int col1, int col2, boolean fillInside, EnumSet<RoundedCorner> roundedCorners, @Nullable ScreenRect scissorArea) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, radius, col1, col2, fillInside, roundedCorners, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    private static @Nullable ScreenRect createBounds(int x0, int y0, int x1, int y1, Matrix3x2fc pose, @Nullable ScreenRect scissorArea) {
        ScreenRect screenRect = (new ScreenRect(x0, y0, x1 - x0, y1 - y0)).transformEachVertex(pose);
        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    public void setupVertices(VertexConsumer vertices) {
        int segments = 12;
        float step = 90f / segments;

        /// QUADS
        // center rectangle
        if (fillInside) {
            vertices.vertex(pose, x0 + radius, y0 + radius).color(col1);
            vertices.vertex(pose, x0 + radius, y1 - radius).color(col1);
            vertices.vertex(pose, x1 - radius, y0 + radius).color(col1);
            vertices.vertex(pose, x1 - radius, y0 + radius).color(col1);
            vertices.vertex(pose, x0 + radius, y1 - radius).color(col1);
            vertices.vertex(pose, x1 - radius, y1 - radius).color(col1);
        }

        // top edge
        vertices.vertex(pose, x0 + radius, y0).color(col2);
        vertices.vertex(pose, x0 + radius, y0 + radius).color(col1);
        vertices.vertex(pose, x1 - radius, y0).color(col2);
        vertices.vertex(pose, x1 - radius, y0).color(col2);
        vertices.vertex(pose, x0 + radius, y0 + radius).color(col1);
        vertices.vertex(pose, x1 - radius, y0 + radius).color(col1);

        // left edge
        vertices.vertex(pose, x0, y0 + radius).color(col2);
        vertices.vertex(pose, x0, y1 - radius).color(col2);
        vertices.vertex(pose, x0 + radius, y0 + radius).color(col1);
        vertices.vertex(pose, x0 + radius, y0 + radius).color(col1);
        vertices.vertex(pose, x0, y1 - radius).color(col2);
        vertices.vertex(pose, x0 + radius, y1 - radius).color(col1);

        // right edge
        vertices.vertex(pose, x1, y0 + radius).color(col2);
        vertices.vertex(pose, x1 - radius, y0 + radius).color(col1);
        vertices.vertex(pose, x1 - radius, y1 - radius).color(col1);
        vertices.vertex(pose, x1, y0 + radius).color(col2);
        vertices.vertex(pose, x1 - radius, y1 - radius).color(col1);
        vertices.vertex(pose, x1, y1 - radius).color(col2);

        // bottom edge
        vertices.vertex(pose, x0 + radius, y1 - radius).color(col1);
        vertices.vertex(pose, x0 + radius, y1).color(col2);
        vertices.vertex(pose, x1 - radius, y1 - radius).color(col1);
        vertices.vertex(pose, x1 - radius, y1 - radius).color(col1);
        vertices.vertex(pose, x0 + radius, y1).color(col2);
        vertices.vertex(pose, x1 - radius, y1).color(col2);

        /// CORNERS
        // top left
        if (roundedCorners.contains(RoundedCorner.TOP_LEFT)) {
            emitCorner(vertices, x0 + radius, y0 + radius, radius, 180f, 270f, segments);
        } else {
            vertices.vertex(pose(), x0, y0).color(col2);
            vertices.vertex(pose(), x0, y0 + radius).color(col2);
            vertices.vertex(pose(), x0 + radius, y0 + radius).color(col1);
            vertices.vertex(pose(), x0 + radius, y0).color(col2);
            vertices.vertex(pose(), x0, y0).color(col2);
            vertices.vertex(pose(), x0 + radius, y0 + radius).color(col1);
        }

        // top right
        if (roundedCorners.contains(RoundedCorner.TOP_RIGHT)) {
            emitCorner(vertices, x1 - radius, y0 + radius, radius, 270f, 360f, segments);
        } else {
            vertices.vertex(pose(), x1, y0).color(col2);
            vertices.vertex(pose(), x1 - radius, y0).color(col2);
            vertices.vertex(pose(), x1 - radius, y0 + radius).color(col1);
            vertices.vertex(pose(), x1, y0).color(col2);
            vertices.vertex(pose(), x1 - radius, y0 + radius).color(col1);
            vertices.vertex(pose(), x1, y0 + radius).color(col2);
        }

        // bottom right
        if (roundedCorners.contains(RoundedCorner.BOTTOM_RIGHT)) {
            emitCorner(vertices, x1 - radius, y1 - radius, radius, 0f, 90f, segments);
        } else {
            vertices.vertex(pose(), x1, y1).color(col2);
            vertices.vertex(pose(), x1 - radius, y1 - radius).color(col1);
            vertices.vertex(pose(), x1 - radius, y1).color(col2);
            vertices.vertex(pose(), x1, y1 - radius).color(col2);
            vertices.vertex(pose(), x1 - radius, y1 - radius).color(col1);
            vertices.vertex(pose(), x1, y1).color(col2);
        }

        // bottom left
        if (roundedCorners.contains(RoundedCorner.BOTTOM_LEFT)) {
            emitCorner(vertices, x0 + radius, y1 - radius, radius, 90f, 180f, segments);
        } else {
            vertices.vertex(pose(), x0 + radius, y1 - radius).color(col1);
            vertices.vertex(pose(), x0, y1 - radius).color(col2);
            vertices.vertex(pose(), x0, y1).color(col2);
            vertices.vertex(pose(), x0 + radius, y1 - radius).color(col1);
            vertices.vertex(pose(), x0, y1).color(col2);
            vertices.vertex(pose(), x0 + radius, y1).color(col2);
        }
    }

    private void emitCorner(VertexConsumer vertices, float cx, float cy, float radius, float startDeg, float endDeg, int segments) {

        float step = (endDeg - startDeg) / segments;

        for (int i = 0; i < segments; i++) {

            float angle1 = (float) Math.toRadians(startDeg + (step * i));
            float angle2 = (float) Math.toRadians(startDeg + (step * (i + 1)));

            float xA = cx + (float) Math.cos(angle1) * radius;
            float yA = cy + (float) Math.sin(angle1) * radius;

            float xB = cx + (float) Math.cos(angle2) * radius;
            float yB = cy + (float) Math.sin(angle2) * radius;

            // COUNTER-CLOCKWISE winding
            vertices.vertex(pose, cx, cy).color(col1);

            vertices.vertex(pose, xB, yB).color(col2);

            vertices.vertex(pose, xA, yA).color(col2);
        }
    }
}