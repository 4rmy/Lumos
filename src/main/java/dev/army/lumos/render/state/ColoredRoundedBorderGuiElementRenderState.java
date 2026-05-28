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
public record ColoredRoundedBorderGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup,
                                                        Matrix3x2fc pose,

                                                        int x0, int y0, int x1, int y1,

                                                        int outerRadius, int borderWidth,

                                                        int col1, int col2,

                                                        EnumSet<RoundedCorner> roundedCorners,

                                                        @Nullable ScreenRect scissorArea,
                                                        @Nullable ScreenRect bounds) implements SimpleGuiElementRenderState {

    public ColoredRoundedBorderGuiElementRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, int x0, int y0, int x1, int y1, int outerRadius, int borderWidth, int col1, int col2, EnumSet<RoundedCorner> roundedCorners, @Nullable ScreenRect scissorArea) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, outerRadius, borderWidth, col1, col2, roundedCorners, scissorArea, createBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    private static @Nullable ScreenRect createBounds(int x0, int y0, int x1, int y1, Matrix3x2fc pose, @Nullable ScreenRect scissorArea) {
        ScreenRect screenRect = (new ScreenRect(x0, y0, x1 - x0, y1 - y0)).transformEachVertex(pose);

        return scissorArea != null ? scissorArea.intersection(screenRect) : screenRect;
    }

    @Override
    public void setupVertices(VertexConsumer vertices) {
        float inner_radius = outerRadius - borderWidth;
        int segments = 12;

        if (roundedCorners.contains(RoundedCorner.TOP_LEFT)) {
            emitCorner(vertices, x0 + outerRadius, y0 + outerRadius, outerRadius, inner_radius, 180f, 270f, segments);
        } else {
            vertices.vertex(pose(), x0, y0).color(col2);
            vertices.vertex(pose(), x0 + borderWidth, y0 + borderWidth).color(col1);
        }
        if (roundedCorners.contains(RoundedCorner.TOP_RIGHT)) {
            emitCorner(vertices, x1 - outerRadius, y0 + outerRadius, outerRadius, inner_radius, 270f, 360f, segments);
        } else {
            vertices.vertex(pose(), x1, y0).color(col2);
            vertices.vertex(pose(), x1 - borderWidth, y0 + borderWidth).color(col1);
        }
        if (roundedCorners.contains(RoundedCorner.BOTTOM_RIGHT)) {
            emitCorner(vertices, x1 - outerRadius, y1 - outerRadius, outerRadius, inner_radius, 0f, 90f, segments);
        } else {
            vertices.vertex(pose(), x1, y1).color(col2);
            vertices.vertex(pose(), x1 - borderWidth, y1 - borderWidth).color(col1);
        }
        if (roundedCorners.contains(RoundedCorner.BOTTOM_LEFT)) {
            emitCorner(vertices, x0 + outerRadius, y1 - outerRadius, outerRadius, inner_radius, 90f, 180f, segments);
        } else {
            vertices.vertex(pose(), x0, y1).color(col2);
            vertices.vertex(pose(), x0 + borderWidth, y1 - borderWidth).color(col1);
        }

        vertices.vertex(pose(), x0, y0 + outerRadius).color(col2);
        vertices.vertex(pose(), x0 + borderWidth, y0 + outerRadius).color(col1);
    }

    private void emitCorner(VertexConsumer vertices, float cx, float cy, float outer_radius, float inner_radius, float startDeg, float endDeg, int segments) {

        float step = (endDeg - startDeg) / segments;

        for (int i = 0; i <= segments; i++) {

            float angle = (float) Math.toRadians(startDeg + (step * i));

            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            // OUTER vertex
            float outerX = cx + cos * outer_radius;
            float outerY = cy + sin * outer_radius;

            // INNER vertex
            float innerX = cx + cos * inner_radius;
            float innerY = cy + sin * inner_radius;

            // Triangle strip ordering:
            // outer -> inner -> outer -> inner ...

            vertices.vertex(pose, outerX, outerY).color(col2);

            vertices.vertex(pose, innerX, innerY).color(col1);
        }
    }
}
