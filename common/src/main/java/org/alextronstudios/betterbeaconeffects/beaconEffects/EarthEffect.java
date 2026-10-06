package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.CustomBeaconRender;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;

public class EarthEffect implements BeaconEffect {

    private static final float BASE_RADIUS = 0.5f;
    private static final float ANGLE = Mth.PI / 8f;

    private static final float TAN = Mth.sin(ANGLE) / Mth.cos(ANGLE);
    private static final float WIDTH = (TAN / CustomBeaconRender.MAX_RENDER_Y) + BASE_RADIUS;

    @Override
    public String getName() {
        return "Earth Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.WOOL.brown();
    }

    @Override
    public int getColor() {
        return 0x3F1F00;
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
        renderEarth(settings, 48f);
    }

    private static void renderEarth(BeaconRenderSettings settings, float radius) {
        settings.poseStack.pushPose();
        settings.poseStack.translate(-radius/16f, -radius/16f, -radius/16f);
        settings.poseStack.scale(1 + radius/8f, 1 + radius/8f, 1 + radius/8f);
//        settings.context.blockEntityRenderDispatcher().renderSingleBlock(Blocks.DIRT.defaultBlockState(), settings.poseStack, settings.submitNodeCollector, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        settings.poseStack.popPose();
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {

        float height = settings.baseHeight + settings.height;
        settings.submitNodeCollector.submitCustomGeometry(settings.poseStack, BetterBeaconRenderTypes.beaconBeamCutout(settings.texture), (pose, vertexConsumer) -> {
            RenderUtils.renderTubePolyTrap(pose, vertexConsumer, 32, (float) settings.baseHeight * WIDTH, height * WIDTH, settings.baseHeight, settings.height, settings.color[0], settings.color[1], settings.color[2], settings.color[2], (settings.beaconRenderState.animationTime) / 10f, -(settings.beaconRenderState.animationTime) / 10f);

        });
    }
}
