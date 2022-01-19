package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class ColorFadeEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Color Fade Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.AMETHYST_BLOCK;
    }

    @Override
    public int getColor() {
        return new Color(127,0,255).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float f = beaconRenderSettings.time + beaconRenderSettings.partialTicks;
        f=(f/100) % 100;
        float s = 0.5F*(Mth.sin((float) (2*Math.PI*f))) + 0.5F;
        s /= 4;
        s += 0.5f;
        Color c = Color.getHSBColor(s, 1, 1);
        beaconRenderSettings.color[0] = c.getRed()/256F;
        beaconRenderSettings.color[1] = c.getGreen()/256F;
        beaconRenderSettings.color[2] = c.getBlue()/256F;
        return beaconRenderSettings;
    }
}
