package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

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
        return new Color(0, 0, 255).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(1,0,1);
        return beaconRenderSettings;
    }
}
