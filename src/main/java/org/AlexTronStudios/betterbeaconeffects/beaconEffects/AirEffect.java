package org.alextronstudios.betterbeaconeffects.beaconEffects;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
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
        return Blocks.WHITE_WOOL;
    }

    @Override
    public int getColor() {
        return 0xFFFFFF;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        beaconRenderSettings.texture = ResourceLocation.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/wind_swirl_white.png");
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
        VertexConsumer vertexConsumer =  settings.multiBufferSource.getBuffer(RenderType.energySwirl(
                ResourceLocation.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/wind_swirl.png"),
                (settings.time + settings.partialTicks) / speed, 0));
        RenderUtils.renderTubeVortex(settings.poseStack.last(), vertexConsumer, new Vector3f(-radius, 0, -radius), new Vector3f(16+radius, 16+radius*2, 16+radius), 1, 1, 1, 1);
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {

        float height = settings.baseHeight + settings.height;
        for (int i=0; i<settings.beams; i++) {
            RenderUtils.renderTubePolyTrap(settings.poseStack.last(), settings.multiBufferSource.getBuffer(BetterBeaconRenderTypes.beaconBeamCutout(settings.texture)), 32, (float) settings.baseHeight * WIDTH + BASE_RADIUS*i, height * WIDTH + BASE_RADIUS*i, settings.baseHeight, settings.height, settings.color[0], settings.color[1], settings.color[2], 1, (settings.time + settings.partialTicks) / 10f, -(settings.time + settings.partialTicks) / 10f);
        }
    }
}
