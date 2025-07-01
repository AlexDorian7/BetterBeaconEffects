package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;

public class ParallaxEffect implements BeaconEffect {

    @Override
    public String getName() {
        return "Parallax Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.REPEATING_COMMAND_BLOCK;
    }

    @Override
    public int getColor() {
        return 0x3F3F3F;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.renderType = BetterBeaconRenderTypes.borderParallax(ResourceLocation.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/cobblestone_height.png"));
        return beaconRenderSettings;
    }
}
