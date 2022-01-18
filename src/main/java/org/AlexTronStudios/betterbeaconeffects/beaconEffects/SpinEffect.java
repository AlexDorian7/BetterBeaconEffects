package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class SpinEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Spin Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.SEA_LANTERN;
    }

    @Override
    public int getColor() {
        return new Color(0, 255, 255).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        PoseStack stack = beaconRenderSettings.poseStack;
        float f = beaconRenderSettings.time + beaconRenderSettings.partialTicks;
        stack.mulPose(Quaternion.fromXYZDegrees(new Vector3f(0,f,0)));
        return beaconRenderSettings;
    }
}
