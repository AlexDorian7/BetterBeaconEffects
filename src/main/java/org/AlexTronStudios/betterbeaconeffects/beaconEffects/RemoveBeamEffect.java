package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class RemoveBeamEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Remove Beam Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.REDSTONE_BLOCK;
    }

    @Override
    public int getColor() {
        return new Color(255, 0, 0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.beams--;
        return beaconRenderSettings;
    }
}
