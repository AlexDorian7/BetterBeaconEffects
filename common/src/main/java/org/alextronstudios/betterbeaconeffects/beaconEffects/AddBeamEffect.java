package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class AddBeamEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Add Beam Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.SLIME_BLOCK;
    }

    @Override
    public int getColor() {
        return 0x00FF00;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.beams++;
        return beaconRenderSettings;
    }
}
