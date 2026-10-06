package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;
import org.joml.Vector3f;

public class AirEffect implements BeaconEffect {

    private static final float BASE_RADIUS = 0.125f;
    private static final float ANGLE = Mth.PI / 8f;

    private static final float WIDTH = Mth.sin(ANGLE) / Mth.cos(ANGLE); // Tan

    @Override
    public String getName() {
        return "Air Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.WOOL.white();
    }

    @Override
    public int getColor() {
        return 0xFFFFFF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.texture = Identifier.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/wind_swirl_white.png");
        return beaconRenderSettings;
    }

    @Override
    public boolean hasCustomRender() {
        return true;
    }

    @Override
    public void customRenderStep(BeaconRenderSettings settings) {
        renderWind(settings, 10f, 4f);
        renderWind(settings, 5f, 2f);
    }

    private static void renderWind(BeaconRenderSettings settings, float speed, float radius) {
        settings.submitNodeCollector.submitCustomGeometry(settings.poseStack, RenderTypes.energySwirl(
                Identifier.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/wind_swirl.png"),
                (settings.beaconRenderState.animationTime) / speed, 0), (pose, vertexConsumer) -> {
            RenderUtils.renderTubeVortex(pose, vertexConsumer, new Vector3f(-radius, 0, -radius), new Vector3f(16+radius, 16+radius*2, 16+radius), 1, 1, 1, 1);
        });
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {

        float height = settings.baseHeight + settings.height;
        for (int i=0; i<settings.beams; i++) {
            int finalI = i;
            settings.submitNodeCollector.submitCustomGeometry(settings.poseStack, BetterBeaconRenderTypes.beaconBeamCutout(settings.texture), (pose, vertexConsumer) -> {
                RenderUtils.renderTubePolyTrap(pose, vertexConsumer, 32, (float) settings.baseHeight * WIDTH + BASE_RADIUS* finalI, height * WIDTH + BASE_RADIUS* finalI, settings.baseHeight, settings.height, settings.color[0], settings.color[1], settings.color[2], 1, (settings.beaconRenderState.animationTime) / 10f, -(settings.beaconRenderState.animationTime) / 10f);
            });
        }
    }
}
