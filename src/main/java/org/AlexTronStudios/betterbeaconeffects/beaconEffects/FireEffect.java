package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.alextronstudios.betterbeaconeffects.CustomBeaconRender;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;

public class FireEffect implements BeaconEffect {

    private static final float BASE_RADIUS = 0.5f;
    private static final float ANGLE = Mth.PI / 8f;

    private static final float TAN = Mth.sin(ANGLE) / Mth.cos(ANGLE);
    private static final float WIDTH = (TAN / CustomBeaconRender.MAX_RENDER_Y) + BASE_RADIUS;

    @Override
    public String getName() {
        return "Fire Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.ORANGE_WOOL;
    }

    @Override
    public int getColor() {
        return 0xFF7F00;
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
        renderFire(settings, 4f);
        renderFire(settings, 2f);
    }

    private static void renderFire(BeaconRenderSettings settings, float radius) {
        settings.poseStack.pushPose();
        settings.poseStack.translate(-radius/16f, 0, -radius/16f);
        settings.poseStack.scale(1 + radius/8f, 1 + radius/8f, 1 + radius/8f);
        settings.context.getBlockRenderDispatcher().renderSingleBlock(Blocks.FIRE.defaultBlockState(), settings.poseStack, settings.multiBufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.CUTOUT);
        settings.poseStack.popPose();
    }

    @Override
    public void customRenderer(BeaconRenderSettings settings) {

        float height = settings.baseHeight + settings.height;
        RenderUtils.renderTubePolyTrap(settings.poseStack.last(), settings.multiBufferSource.getBuffer(BetterBeaconRenderTypes.beaconBeamCutout(settings.texture)), 32, (float) settings.baseHeight * WIDTH, height * WIDTH, settings.baseHeight, settings.height, settings.color[0], settings.color[1], settings.color[2], settings.color[2], (settings.time + settings.partialTicks) / 10f, -(settings.time + settings.partialTicks) / 10f);
    }
}
