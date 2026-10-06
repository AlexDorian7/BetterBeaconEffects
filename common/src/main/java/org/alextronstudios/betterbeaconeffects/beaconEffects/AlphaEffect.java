package org.alextronstudios.betterbeaconeffects.beaconEffects;

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
        return 0x00FFFF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.alpha += 0.125F;
        return beaconRenderSettings;
    }
}
