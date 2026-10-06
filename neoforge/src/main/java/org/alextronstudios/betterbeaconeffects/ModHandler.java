package org.alextronstudios.betterbeaconeffects;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

@Mod(Constants.MOD_ID)
public class ModHandler {

    public ModHandler(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerPipelines);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        BetterBeaconEffects.init();
        BlockEntityRenderers.register(BlockEntityTypes.BEACON, NeoForgeCustomBeaconRender::new);
    }

    public void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(BetterBeaconRenderTypes.COLORED_PORTAL_PIPELINE);
    }
}