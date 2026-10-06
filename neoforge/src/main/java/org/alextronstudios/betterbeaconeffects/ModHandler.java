package org.alextronstudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

import java.io.IOException;

@Mod(Constants.MOD_ID)
public class ModHandler {

    public ModHandler(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(ModHandler::registerShaders);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        BetterBeaconEffects.init();
        BlockEntityRenderers.register(BlockEntityType.BEACON, CustomBeaconRender::new);
    }


    public static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_colored_portal"), DefaultVertexFormat.POSITION_COLOR), (shader) -> BetterBeaconRenderTypes.RENDERTYPE_COLORED_PORTAL_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_beacon_beam_cutout"), DefaultVertexFormat.BLOCK), (shader) -> BetterBeaconRenderTypes.RENDERTYPE_BEACON_BEAM_CUTOUT_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.withDefaultNamespace("rendertype_border_parallax"), DefaultVertexFormat.BLOCK), (shader) -> BetterBeaconRenderTypes.RENDERTYPE_BORDER_PARALLAX_SHADER = shader);
        } catch (IOException e) {
            throw new RuntimeException("could not reload better beacon effect shaders", e);
        }
    }
}