package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

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
        return new Color(255, 232, 119).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float[] color = beaconRenderSettings.color;
        PoseStack stack = beaconRenderSettings.poseStack;
        stack.translate(color[0], color[1], color[2]);
        return beaconRenderSettings;
    }
}
