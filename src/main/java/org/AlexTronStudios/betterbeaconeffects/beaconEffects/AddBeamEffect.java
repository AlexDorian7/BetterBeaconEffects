package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class AddBeamEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Add Beam Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.SLIME_BLOCK;
    }

    @Override
    public int getColor() {
        return new Color(0, 255, 0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.beams++;
        return beaconRenderSettings;
    }
}
