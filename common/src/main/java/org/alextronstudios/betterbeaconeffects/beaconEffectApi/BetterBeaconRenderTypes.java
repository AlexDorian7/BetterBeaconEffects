package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.alextronstudios.betterbeaconeffects.platform.Services;

import java.util.function.Function;

public class BetterBeaconRenderTypes {
    public static ShaderInstance RENDERTYPE_COLORED_PORTAL_SHADER;
    public static ShaderInstance RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER;
    public static ShaderInstance RENDERTYPE_BORDER_PARALLAX_SHADER;

    public static ShaderInstance getBeaconBeamCutoutShader() {
        return RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER;
    }

    public static ShaderInstance getColoredPortalShader() {
        return RENDERTYPE_COLORED_PORTAL_SHADER;
    }

    public static ShaderInstance getRendertypeBorderParallaxShader() {
        return RENDERTYPE_BORDER_PARALLAX_SHADER;
    }

    public static final RenderType COLORED_PORTAL = Services.PLATFORM.getColoredPortal();

    private static final Function<ResourceLocation, RenderType> BEACON_BEAM_TRANSLUCENT = Services.PLATFORM.getBeaconBeamTranslucent();

    private static final Function<ResourceLocation, RenderType> BEACON_BEAM_CUTOUT = Services.PLATFORM.getBeaconBeamCutout();

    private static final Function<ResourceLocation, RenderType> BORDER_PARALLAX = Services.PLATFORM.getBorderParallax();

    public static RenderType beaconBeamTranslucent(ResourceLocation texture) {
        return BEACON_BEAM_TRANSLUCENT.apply(texture);
    }

    public static RenderType beaconBeamCutout(ResourceLocation texture) {
        return BEACON_BEAM_CUTOUT.apply(texture);
    }

    public static RenderType borderParallax(ResourceLocation texture) {
        return BORDER_PARALLAX.apply(texture);
    }
}
