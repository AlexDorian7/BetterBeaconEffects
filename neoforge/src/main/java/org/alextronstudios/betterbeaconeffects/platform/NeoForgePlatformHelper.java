package org.alextronstudios.betterbeaconeffects.platform;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
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