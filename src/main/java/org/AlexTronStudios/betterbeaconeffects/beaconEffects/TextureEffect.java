package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;

public class TextureEffect implements BeaconEffect {

    public static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");
    @Override
    public String getName() {
        return "Texture Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.OBSERVER;
    }

    @Override
    public int getColor() {
        return new Color(127, 127, 127).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.texture = TEXTURE;
        beaconRenderSettings.recalculateRenderType();
        return beaconRenderSettings;
    }
}
