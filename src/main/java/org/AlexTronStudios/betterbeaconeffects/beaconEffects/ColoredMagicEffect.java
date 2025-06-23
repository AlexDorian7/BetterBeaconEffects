package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.FastColor;
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
        return FastColor.ARGB32.color(255, 255, 191, 100);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = BetterBeaconRenderTypes.getInstance().COLORED_PORTAL;
        return beaconRenderSettings;
    }
}
