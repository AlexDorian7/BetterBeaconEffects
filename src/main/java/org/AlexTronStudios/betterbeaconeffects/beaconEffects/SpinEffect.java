package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.joml.Quaternionf;

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
        stack.mulPose(new Quaternionf().setAngleAxis(f, 0, 1, 0));
        return beaconRenderSettings;
    }
}
