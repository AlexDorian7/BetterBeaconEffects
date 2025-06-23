package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

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
        return FastColor.ARGB32.color(255, 255, 100);
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = RenderType.endPortal();
        return beaconRenderSettings;
    }
}
