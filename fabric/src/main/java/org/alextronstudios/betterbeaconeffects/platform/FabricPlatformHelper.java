package org.alextronstudios.betterbeaconeffects.platform;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public RenderType getColoredPortal() {
        return RenderType.create(
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
    }

    @Override
    public Function<ResourceLocation, RenderType> getBeaconBeamTranslucent() {
        return Util.memoize(
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
    }

    @Override
    public Function<ResourceLocation, RenderType> getBeaconBeamCutout() {
        return Util.memoize(
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
    }

    @Override
    public Function<ResourceLocation, RenderType> getBorderParallax() {
        return Util.memoize(
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
    }
}
