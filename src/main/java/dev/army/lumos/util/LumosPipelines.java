package dev.army.lumos.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.army.lumos.client.LumosClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class LumosPipelines {
    public static final RenderPipeline GUI_TRIANGLES = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
            .withLocation(Identifier.of(LumosClient.getModId(), "pipeline/gui_triangles"))
            .withUsePipelineDrawModeForGui(true)
            .withVertexFormat(
                    VertexFormats.POSITION_COLOR,
                    VertexFormat.DrawMode.TRIANGLES
            )
            .build()
    );

    public static final RenderPipeline GUI_STRIP = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
            .withLocation(Identifier.of(LumosClient.getModId(), "pipeline/gui_strip"))
            .withUsePipelineDrawModeForGui(true)
            .withVertexFormat(
                    VertexFormats.POSITION_COLOR,
                    VertexFormat.DrawMode.TRIANGLE_STRIP
            )
            .build()
    );
}
