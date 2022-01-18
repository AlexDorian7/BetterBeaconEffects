package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class AlphaEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Alpha Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.PRISMARINE;
    }

    @Override
    public int getColor() {
        return new Color(0, 255, 255).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.alpha += 0.125F;
        return beaconRenderSettings;
    }
}
