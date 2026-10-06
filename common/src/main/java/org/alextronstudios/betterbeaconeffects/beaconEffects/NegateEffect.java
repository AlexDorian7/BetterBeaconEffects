package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class NegateEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Negate Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.TINTED_GLASS;
    }

    @Override
    public int getColor() {
        return FastColor.ARGB32.color(0,0,0);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.color[0] = 256-beaconRenderSettings.color[0];
        beaconRenderSettings.color[1] = 256-beaconRenderSettings.color[1];
        beaconRenderSettings.color[2] = 256-beaconRenderSettings.color[2];
        return beaconRenderSettings;
    }
}
