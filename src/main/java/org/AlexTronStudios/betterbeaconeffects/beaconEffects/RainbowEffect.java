package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class RainbowEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Rainbow Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.OBSIDIAN;
    }

    @Override
    public int getColor() {
        return new Color(0,0,0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float f = beaconRenderSettings.time + beaconRenderSettings.partialTicks;
        f=(f/100) % 360;
        Color c = Color.getHSBColor(f, 1, 1);
        beaconRenderSettings.color[0] = c.getRed()/256F;
        beaconRenderSettings.color[1] = c.getGreen()/256F;
        beaconRenderSettings.color[2] = c.getBlue()/256F;
        return beaconRenderSettings;
    }
}
