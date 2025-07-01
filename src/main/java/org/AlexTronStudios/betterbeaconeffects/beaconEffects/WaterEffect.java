package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.CustomBeaconRender;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;

import java.util.Random;

public class WaterEffect implements BeaconEffect {

    private static final float BASE_RADIUS = 0.5f;
    private static final float ANGLE = Mth.PI / 8f;

    private static final float TAN = Mth.sin(ANGLE) / Mth.cos(ANGLE);
    private static final float WIDTH = (TAN / CustomBeaconRender.MAX_RENDER_Y) + BASE_RADIUS;

    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "Water Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.BLUE_WOOL;
    }

    @Override
    public int getColor() {
        return 0x0000FF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        //beaconRenderSettings.texture = ResourceLocation.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/wind_swirl_white.png");
        return beaconRenderSettings;
    }

    @Override
    public boolean hasCustomRender() {
        return false;
    }

    @Override
    public void customRenderStep(BeaconRenderSettings settings) {
        renderWater(settings);
        renderWater(settings);
    }

    private static void renderWater(BeaconRenderSettings settings) {
        if (RANDOM.nextInt(4) == 0) {
            BlockPos pos = settings.blockEntity.getBlockPos();
            float x = RANDOM.nextFloat()*7+pos.getX()-3;
            float y = RANDOM.nextFloat()*7+pos.getY()-3;
            float z = RANDOM.nextFloat()*7+pos.getZ()-3;
            settings.blockEntity.getLevel().addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {

        float height = settings.baseHeight + settings.height;
        RenderUtils.renderTubePolyTrap(settings.poseStack.last(), settings.multiBufferSource.getBuffer(BetterBeaconRenderTypes.beaconBeamCutout(settings.texture)), 32, (float) settings.baseHeight * WIDTH, height * WIDTH, settings.baseHeight, settings.height, settings.color[0], settings.color[1], settings.color[2], settings.color[2], (settings.time + settings.partialTicks) / 10f, -(settings.time + settings.partialTicks) / 10f);
    }
}
