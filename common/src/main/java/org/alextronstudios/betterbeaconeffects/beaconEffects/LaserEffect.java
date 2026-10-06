package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.math.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

public class LaserEffect implements BeaconEffect {
    @Override
    public String getName() {
        return "LASER Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.COPPER_BLOCK.waxed().unaffected();
    }

    @Override
    public int getColor() {
        return 0xFFBF00;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.poseStack.translate(0,0.5F,0);
        beaconRenderSettings.poseStack.rotateDegrees(Axis.XP, 90);
        beaconRenderSettings.poseStack.translate(0,-0.5F,0);
        return beaconRenderSettings;
    }
}
