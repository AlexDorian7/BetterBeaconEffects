package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class LineEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Line Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.CHAIN;
    }

    @Override
    public int getColor() {
        return 0xFFFFFF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = RenderType.lines();
        return beaconRenderSettings;
    }
}
