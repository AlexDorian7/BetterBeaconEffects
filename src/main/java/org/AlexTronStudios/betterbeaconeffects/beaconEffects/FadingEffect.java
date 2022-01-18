package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class FadingEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Fading Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.PURPUR_BLOCK;
    }

    @Override
    public int getColor() {
        return new Color(234,119,255).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float f = beaconRenderSettings.time + beaconRenderSettings.partialTicks;
        f=(f/100) % 100;
        float s = 0.5F*(Mth.sin((float) (2*Math.PI*f))) + 0.5F;
        beaconRenderSettings.alpha = s;
        return beaconRenderSettings;
    }
}
