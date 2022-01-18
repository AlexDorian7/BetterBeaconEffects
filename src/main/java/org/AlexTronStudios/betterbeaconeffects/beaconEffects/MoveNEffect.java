package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

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
        return new Color(0, 0, 0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(-1,0,-1);
        return beaconRenderSettings;
    }
}
