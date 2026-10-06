package org.alextronstudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

import java.io.IOException;

public class ModHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BetterBeaconEffects.init();

        BlockEntityRenderers.register(
                BlockEntityType.BEACON,
                CustomBeaconRender::new
        );

        CoreShaderRegistrationCallback.EVENT.register(ModHandler::registerShaders);
    }

    private static void registerShaders(CoreShaderRegistrationCallback.RegistrationContext context)
            throws IOException {

        context.register(
                ResourceLocation.withDefaultNamespace("rendertype_colored_portal"),
                DefaultVertexFormat.POSITION_COLOR,
                shader -> BetterBeaconRenderTypes.RENDERTYPE_COLORED_PORTAL_SHADER = shader
        );

        context.register(
                ResourceLocation.withDefaultNamespace("rendertype_beacon_beam_cutout"),
                DefaultVertexFormat.BLOCK,
                shader -> BetterBeaconRenderTypes.RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER = shader
        );

        context.register(
                ResourceLocation.withDefaultNamespace("rendertype_border_parallax"),
                DefaultVertexFormat.BLOCK,
                shader -> BetterBeaconRenderTypes.RENDERTYPE_BORDER_PARALLAX_SHADER = shader
        );
    }
}