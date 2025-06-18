package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.joml.Quaternionf;

import java.awt.*;

public class LaserEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "LASER Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.WAXED_COPPER_BLOCK;
    }

    @Override
    public int getColor() {
        return new Color(255, 191, 0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(0,0.5F,0);
        beaconRenderSettings.poseStack.mulPose(new Quaternionf().setAngleAxis((Math.PI/2), 1, 0, 0));
        beaconRenderSettings.poseStack.translate(0,-0.5F,0);
        return beaconRenderSettings;
    }
}
