package org.alextronstudios.betterbeaconeffects;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public class ModHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BetterBeaconEffects.init();

        BlockEntityRenderers.register(
                BlockEntityTypes.BEACON,
                CustomBeaconRender::new
        );
    }
}