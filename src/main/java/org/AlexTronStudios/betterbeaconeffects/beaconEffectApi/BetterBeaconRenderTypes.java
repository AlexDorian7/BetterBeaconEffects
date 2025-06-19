package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;


public class BetterBeaconRenderTypes {
    public static final BetterBeaconRenderTypes INSTANCE = new BetterBeaconRenderTypes();

    private BetterBeaconRenderTypes() {}

    public static BetterBeaconRenderTypes getInstance() {
        return INSTANCE;
    }

    private static ShaderInstance RENDERTYPE_COLORED_PORTAL_SHADER;
    public final RenderType COLORED_PORTAL = RenderType.create(
            "colored_portal",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(this::getColoredPortalShader))
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                                    .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                    .build()
                    )
                    .createCompositeState(false)
    );

    public ShaderInstance getColoredPortalShader() {
        return RENDERTYPE_COLORED_PORTAL_SHADER;
    }

    public void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_colored_portal"), DefaultVertexFormat.POSITION_COLOR), (shader) -> RENDERTYPE_COLORED_PORTAL_SHADER = shader);
        } catch (IOException e) {
            throw new RuntimeException("could not reload better beacon effect shaders", e);
        }
    }
}
