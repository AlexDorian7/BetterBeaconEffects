package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class TextureEffect implements BeaconEffect {

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
        return 0x7F7F7F;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.texture = BeaconRenderer.BEAM_LOCATION;
        beaconRenderSettings.recalculateRenderType();
        return beaconRenderSettings;
    }
}
