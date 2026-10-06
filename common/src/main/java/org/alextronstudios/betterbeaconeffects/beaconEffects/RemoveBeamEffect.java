package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class RemoveBeamEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Remove Beam Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.REDSTONE_BLOCK;
    }

    @Override
    public int getColor() {
        return 0xFF0000;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.beams--;
        return beaconRenderSettings;
    }
}
