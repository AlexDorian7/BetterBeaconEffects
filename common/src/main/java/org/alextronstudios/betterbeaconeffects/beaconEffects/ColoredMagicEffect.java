package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

public class ColoredMagicEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Colored Magic Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.END_STONE_BRICKS;
    }

    @Override
    public int getColor() {
        return 0xFFBF64;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = BetterBeaconRenderTypes.COLORED_PORTAL;
        return beaconRenderSettings;
    }
}
