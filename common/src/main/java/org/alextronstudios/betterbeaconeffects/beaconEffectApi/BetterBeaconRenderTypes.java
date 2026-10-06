package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.alextronstudios.betterbeaconeffects.platform.Services;

public class BetterBeaconRenderTypes {

    private static final RenderPipeline.Snippet GLOBALS_SNIPPET = RenderPipeline.builder().withBindGroupLayout(BindGroupLayouts.GLOBALS).buildSnippet();

    private static final RenderPipeline.Snippet END_PORTAL_SNIPPET = RenderPipeline.builder(GLOBALS_SNIPPET)
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            .withVertexShader("core/rendertype_colored_portal")
            .withFragmentShader("core/rendertype_colored_portal")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .buildSnippet();

    public static final RenderPipeline COLORED_PORTAL_PIPELINE = Services.PLATFORM.register(
            RenderPipeline.builder(END_PORTAL_SNIPPET).withLocation("pipeline/colored_portal").withShaderDefine("PORTAL_LAYERS", 15).build()
    );

    public static RenderSetup COLORED_PORTAL_SETUP = RenderSetup.builder(COLORED_PORTAL_PIPELINE)
            .withTexture("Sampler0", AbstractEndPortalRenderer.END_SKY_LOCATION)
            .withTexture("Sampler1", AbstractEndPortalRenderer.END_PORTAL_LOCATION)
            .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
            .createRenderSetup();

    public static final RenderType COLORED_PORTAL = Services.PLATFORM.create("colored_portal", COLORED_PORTAL_SETUP);

    public static RenderType beaconBeamTranslucent(Identifier texture) {
        return RenderTypes.beaconBeam(texture, true);
    }

    public static RenderType beaconBeamCutout(Identifier texture) {
        return RenderTypes.beaconBeam(texture, false);
    }
}
