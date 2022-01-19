package org.AlexTronStudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffectRegistry;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.AlexTronStudios.betterbeaconeffects.utils.Pair;
import org.AlexTronStudios.betterbeaconeffects.utils.RenderUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomBeaconRender implements BlockEntityRenderer<BeaconBlockEntity> {

    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam_no_texture.png");
    public static final ResourceLocation TEXTURE_OUT = new ResourceLocation("textures/misc/beacon_out.png");
    public static final ResourceLocation TEXTURE_IN = new ResourceLocation("textures/misc/beacon_in.png");
    public static final int MAX_RENDER_Y = 1024;

    private static final float PiSin = (float) Math.sin((Math.PI / 4D));

    public CustomBeaconRender(BlockEntityRendererProvider.Context p_173529_) {
    }

    @Override
    public void render(BeaconBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int overlayIn) {
        long i = blockEntity.getLevel().getGameTime();

        renderNetherStar(poseStack, multiBufferSource, 4, i, partialTicks); //Render the star inside

        List<BeaconBlockEntity.BeaconBeamSection> list = blockEntity.getBeamSections();
        int j = 0;

        List<Pair<ResourceLocation, Block>> blocks = BeaconEffectRegistry.getBlockRegistry();
        List<ResourceLocation> activeEffects = new ArrayList<>();

        BeaconEffect renderer = null;

        for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
                for (int z = -3; z < 4; z++) {
                    for (Pair<ResourceLocation, Block> block : blocks) {
                        BlockPos pos = blockEntity.getBlockPos().offset(x,y,z);
                        if (blockEntity.getLevel().getBlockState(pos).getBlock().equals(block.second)) {
                            activeEffects.add(block.first);
                        }
                    }
                }
            }
        }
        BeaconRenderSettings settings = new BeaconRenderSettings(blockEntity, poseStack, multiBufferSource, partialTicks, i, 0, 0, new float[]{0, 0, 0}, new ResourceLocation(BEAM_LOCATION.getNamespace(), BEAM_LOCATION.getPath()), 0.125F, 5);
        for (ResourceLocation activeEffect : activeEffects) {
            BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            if (effect.hasCustomRender()) {
                renderer = effect;
            }
            effect.customRenderStep(settings);
        }

        for(int k = 0; k < list.size(); ++k) {
            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            renderBeaconBeam(blockEntity, poseStack, multiBufferSource, partialTicks, i, j, k == list.size() - 1 ? MAX_RENDER_Y : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor(), activeEffects, renderer);
            j += beaconblockentity$beaconbeamsection.getHeight();
        }
    }

    public static void renderBeaconBeam(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, float[] color, List<ResourceLocation> activeEffects, @Nullable BeaconEffect renderer) {

        poseStack.pushPose();

        poseStack.translate(0.5F,0,0.5F);

        float[] c = {color[0], color[1], color[2]};
        BeaconRenderSettings settings = new BeaconRenderSettings(blockEntity, poseStack, multiBufferSource, partialTicks, time, baseHeight, height, c, new ResourceLocation(BEAM_LOCATION.getNamespace(), BEAM_LOCATION.getPath()), 0.125F, 5);

        for (ResourceLocation activeEffect : activeEffects) {
            BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            if (effect.hasCustomRender()) {
                renderer = effect;
            }
            effect.alterRenderer(settings);
        }

        if (renderer != null) {
            renderer.customRenderer(settings);
        } else {
            for (int i=0; i<settings.beams; i++) {
                float s = i*4+4;
                float s1 = s/2;
                RenderUtils.renderTube(poseStack.last().pose(), multiBufferSource.getBuffer(settings.renderType), new Vector3f(-s1, settings.baseHeight*16, -s1), new Vector3f(s1, settings.height*16, s1), settings.color[0], settings.color[1], settings.color[2], settings.alpha, 0, settings.time/5F, 1, height+(settings.time/5F), 15728880);
            }
        }

        poseStack.popPose();
    }

    private static void renderNetherStar(PoseStack matrixStackIn, MultiBufferSource bufferIn, float radius,
                                         long totalWorldTime, float partialTicks) {
        renderNetherStar(matrixStackIn, bufferIn, 1F, 1F, 1F, 1F, radius, totalWorldTime, partialTicks);
    }

    public static float sinWave(long totalWorldTime, float partialTicks) {
        float f = (float) totalWorldTime + partialTicks;
        float f1 = Mth.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 2.4F;
    }

    private static void renderNetherStar(PoseStack matrixStackIn, MultiBufferSource bufferIn, float red, float green,
                                         float blue, float alpha, float radius, long totalWorldTime, float partialTicks) {
        float f = (float) Math.floorMod(totalWorldTime, 160L) + partialTicks;
        matrixStackIn.pushPose  ();
        matrixStackIn.translate(0.5F, 0.125F, 0.5F);
        float f1 = (totalWorldTime + partialTicks) * 3.0F;
        float f2 = sinWave(totalWorldTime, partialTicks);
        matrixStackIn.translate(0.0D, 1.5F + f2 / 2.0F, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        matrixStackIn.mulPose(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.scale(0.875F, 0.875F, 0.875F);
        matrixStackIn.mulPose(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.scale(0.875F, 0.875F, 0.875F);
        matrixStackIn.mulPose(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_IN)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_IN)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(BeaconBlockEntity p_112306_) {
        return true;
    }

    @Override
    public boolean shouldRender(BeaconBlockEntity p_173568_, Vec3 p_173569_) {
        return true;
    }
}
