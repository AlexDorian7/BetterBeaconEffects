package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

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
        return 0x00FFFF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        PoseStack stack = beaconRenderSettings.poseStack;
        stack.rotateDegrees(Axis.YP, beaconRenderSettings.state.animationTime * 2.25F - 45.0F);
        return beaconRenderSettings;
    }
}
