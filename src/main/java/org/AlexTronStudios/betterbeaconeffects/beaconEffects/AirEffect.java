package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class AirEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "Air Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.WHITE_WOOL;
    }

    @Override
    public int getColor() {
        return FastColor.ARGB32.color(255, 255, 255);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        return beaconRenderSettings;
    }

    @Override
    public boolean hasCustomRender() {
        return true;
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {
        BeaconEffect.super.customRenderer(settings);
    }

    @Override
    public void customRenderStep(BeaconRenderSettings settings) {
        BeaconEffect.super.customRenderStep(settings);
    }
}
