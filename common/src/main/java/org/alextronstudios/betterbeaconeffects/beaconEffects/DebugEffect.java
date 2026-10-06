package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class DebugEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Debug Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.COMMAND_BLOCK;
    }

    @Override
    public int getColor() {
        return FastColor.ARGB32.color(255, 232, 119);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float[] color = beaconRenderSettings.color;
        PoseStack stack = beaconRenderSettings.poseStack;
        stack.translate(color[0], color[1], color[2]);
        return beaconRenderSettings;
    }
}
