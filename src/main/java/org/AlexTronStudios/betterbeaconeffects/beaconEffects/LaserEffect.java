package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

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
        beaconRenderSettings.poseStack.mulPose(Quaternion.fromXYZDegrees(new Vector3f(90,0,0)));
        beaconRenderSettings.poseStack.translate(0,-0.5F,0);
        return beaconRenderSettings;
    }
}
