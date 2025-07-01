package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class MovePEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "MoveP Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.LAPIS_BLOCK;
    }

    @Override
    public int getColor() {
        return 0x0000FF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(1,0,1);
        return beaconRenderSettings;
    }
}
