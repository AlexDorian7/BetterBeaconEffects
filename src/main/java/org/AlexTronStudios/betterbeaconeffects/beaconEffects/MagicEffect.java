package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class MagicEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Magic Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.END_STONE;
    }

    @Override
    public int getColor() {
        return new Color(255, 255, 100).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = RenderType.endPortal();
        return beaconRenderSettings;
    }
}
