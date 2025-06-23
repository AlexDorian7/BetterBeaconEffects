package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class MoveNEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "MoveN Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.COAL_BLOCK;
    }

    @Override
    public int getColor() {
        return FastColor.ARGB32.color(0, 0, 0);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(-1,0,-1);
        return beaconRenderSettings;
    }
}
