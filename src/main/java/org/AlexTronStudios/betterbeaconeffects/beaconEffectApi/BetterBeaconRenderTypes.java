package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;


public class BetterBeaconRenderTypes {
    private static ShaderInstance RENDERTYPE_COLORED_PORTAL_SHADER;
    private static ShaderInstance RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER;
    private static ShaderInstance RENDERTYPE_BORDER_PARALLAX_SHADER;

    private static ShaderInstance getBeaconBeamCutoutShader() {
        return RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER;
    }

    private static ShaderInstance getColoredPortalShader() {
        return RENDERTYPE_COLORED_PORTAL_SHADER;
    }

    private static ShaderInstance getRendertypeBorderParallaxShader() {
        return RENDERTYPE_BORDER_PARALLAX_SHADER;
    }

    public static final RenderType COLORED_PORTAL = RenderType.create(
            "colored_portal",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(BetterBeaconRenderTypes::getColoredPortalShader))
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                                    .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                    .build()
                    )
                    .createCompositeState(false)
    );

    private static final Function<ResourceLocation, RenderType> BEACON_BEAM_TRANSLUCENT = Util.memoize(
            (texture) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setWriteMaskState(COLOR_WRITE)
                        .createCompositeState(false);
                return RenderType.create("beacon_beam", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, false, true, rendertype$compositestate);
            }
    );

    private static final Function<ResourceLocation, RenderType> BEACON_BEAM_CUTOUT = Util.memoize(
            (texture) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(new ShaderStateShard(BetterBeaconRenderTypes::getBeaconBeamCutoutShader))
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(NO_TRANSPARENCY)
                        .setWriteMaskState(COLOR_DEPTH_WRITE)
                        .createCompositeState(false);
                return RenderType.create("beacon_beam_cutout", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, false, true, rendertype$compositestate);
            }
    );

    private static final Function<ResourceLocation, RenderType> BORDER_PARALLAX = Util.memoize(
            (texture) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(new ShaderStateShard(BetterBeaconRenderTypes::getRendertypeBorderParallaxShader))
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(NO_TRANSPARENCY)
                        .setWriteMaskState(COLOR_DEPTH_WRITE)
                        .createCompositeState(false);
                return RenderType.create("border_parallax", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, false, true, rendertype$compositestate);
            }
    );

    public static RenderType beaconBeamTranslucent(ResourceLocation texture) {
        return BEACON_BEAM_TRANSLUCENT.apply(texture);
    }

    public static RenderType beaconBeamCutout(ResourceLocation texture) {
        return BEACON_BEAM_CUTOUT.apply(texture);
    }

    public static RenderType borderParallax(ResourceLocation texture) {
        return BORDER_PARALLAX.apply(texture);
    }

    public static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_colored_portal"), DefaultVertexFormat.POSITION_COLOR), (shader) -> RENDERTYPE_COLORED_PORTAL_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_beacon_beam_cutout"), DefaultVertexFormat.BLOCK), (shader) -> RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_border_parallax"), DefaultVertexFormat.BLOCK), (shader) -> RENDERTYPE_BORDER_PARALLAX_SHADER = shader);
        } catch (IOException e) {
            throw new RuntimeException("could not reload better beacon effect shaders", e);
        }
    }
}
