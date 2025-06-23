package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class AlphaEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Alpha Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.PRISMARINE;
    }

    @Override
    public int getColor() {
        return FastColor.ARGB32.color(0, 255, 255);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.alpha += 0.125F;
        return beaconRenderSettings;
    }
}
