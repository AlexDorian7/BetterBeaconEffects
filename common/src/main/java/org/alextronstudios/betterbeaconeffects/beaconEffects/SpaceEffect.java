package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

public class SpaceEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Space Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.CONCRETE.white();
    }

    @Override
    public int getColor() {
        return 0xFFBF64;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = BetterBeaconRenderTypes.SPACE;
        return beaconRenderSettings;
    }
}
