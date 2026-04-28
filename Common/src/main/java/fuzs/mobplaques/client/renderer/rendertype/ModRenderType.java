package fuzs.mobplaques.client.renderer.rendertype;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import fuzs.mobplaques.MobPlaques;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class ModRenderType {
    /**
     * Disable depth write as it prevents water behind the text background from rendering.
     *
     * @see RenderPipelines#TEXT_BACKGROUND
     */
    public static final RenderPipeline TEXT_BACKGROUND_PIPELINE = RenderPipeline.builder(RenderPipelines.TEXT_SNIPPET,
                    RenderPipelines.FOG_SNIPPET)
            .withLocation(MobPlaques.id("pipeline/text_background"))
            .withVertexShader("core/rendertype_text_background")
            .withFragmentShader("core/rendertype_text_background")
            .withSampler("Sampler2")
            .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS)
            .build();
    /**
     * @see RenderType#TEXT_BACKGROUND
     */
    private static final RenderType TEXT_BACKGROUND = RenderType.create(MobPlaques.id("text_background").toString(),
            RenderSetup.builder(TEXT_BACKGROUND_PIPELINE).useLightmap().sortOnUpload().createRenderSetup());

    private ModRenderType() {
        // NO-OP
    }

    public static RenderType textBackground() {
        return TEXT_BACKGROUND;
    }
}
