package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class NegateEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Negate Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.TINTED_GLASS;
    }

    @Override
    public int getColor() {
        return new Color(0,0,0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.color[0] = 256-beaconRenderSettings.color[0];
        beaconRenderSettings.color[1] = 256-beaconRenderSettings.color[1];
        beaconRenderSettings.color[2] = 256-beaconRenderSettings.color[2];
        return beaconRenderSettings;
    }
}
