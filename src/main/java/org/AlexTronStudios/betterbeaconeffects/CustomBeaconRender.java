package org.alextronstudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffectRegistry;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.utils.Pair;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomBeaconRender implements BlockEntityRenderer<BeaconBlockEntity> {
    public static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam_no_texture.png");
    public static final ResourceLocation TEXTURE_OUT = ResourceLocation.withDefaultNamespace("textures/misc/beacon_out.png");
    public static final ResourceLocation TEXTURE_IN = ResourceLocation.withDefaultNamespace("textures/misc/beacon_in.png");
    public static final int MAX_RENDER_Y = 1024;

    private static final Vector3f YP = new Vector3f(0,1,0);

    private static final float PiSin = (float) Math.sin((Math.PI / 4D));

    public CustomBeaconRender(BlockEntityRendererProvider.Context p_173529_) {
    }

    @Override
    public void render(BeaconBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int overlayIn) {
        long i = blockEntity.getLevel().getGameTime();

        renderNetherStar(poseStack, multiBufferSource, 4, i, partialTicks); //Render the star inside

        List<Pair<ResourceLocation, Block>> blocks = BeaconEffectRegistry.getBlockRegistry();
        List<ResourceLocation> activeEffects = new ArrayList<>();

        BeaconEffect renderer = null;

        boolean glassRendering = false;

        BlockPos glassPos = blockEntity.getBlockPos().offset(0,1,0);
        if (blockEntity.getLevel().getBlockState(glassPos).getBlock().equals(Blocks.GLASS)) {
            glassRendering = true;
            RenderUtils.renderLineCube(poseStack, multiBufferSource, new Vector3f(-3.001f, -3.001f, -3.001f), new Vector3f(4.001f, 4.001f, 4.001f), 1, 1, 1, 1);
        }

        for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
                for (int z = -3; z < 4; z++) {
                    for (Pair<ResourceLocation, Block> block : blocks) {
                        BlockPos pos = blockEntity.getBlockPos().offset(x,y,z);
                        if (blockEntity.getLevel().getBlockState(pos).getBlock().equals(block.second)) {
                            activeEffects.add(block.first);
                            if (glassRendering) {
                                BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(block.first);
                                RenderUtils.renderLineCube(poseStack, multiBufferSource, new Vector3f(x-0.001f, y-0.001f, z-0.001f), new Vector3f(x+1.001f, y+1.001f, z+1.001f), ((effect.getColor()>>16)&0xFF)/256F,((effect.getColor()>>8)&0xFF)/256F, (effect.getColor()&0xFF)/256F, 1);
                            }
                        }
                    }
                }
            }
        }
        BeaconRenderSettings settings = new BeaconRenderSettings(blockEntity, poseStack, multiBufferSource, partialTicks, i, 0, 0, new float[]{0, 0, 0}, ResourceLocation.fromNamespaceAndPath(BEAM_LOCATION.getNamespace(), BEAM_LOCATION.getPath()), 0.125F, 5);
        for (ResourceLocation activeEffect : activeEffects) {
            BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            if (effect.hasCustomRender()) {
                renderer = effect;
            }
            effect.customRenderStep(settings);
        }

        List<BeaconBlockEntity.BeaconBeamSection> list = blockEntity.getBeamSections();
        int j = 0;

        for(int k = 0; k < list.size(); ++k) {
            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            renderBeaconBeam(blockEntity, poseStack, multiBufferSource, partialTicks, i, j, k == list.size() - 1 ? MAX_RENDER_Y : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor(), activeEffects, renderer);
            j += beaconblockentity$beaconbeamsection.getHeight();
        }
    }

    public static void renderBeaconBeam(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, int color, List<ResourceLocation> activeEffects, @Nullable BeaconEffect renderer) {

        poseStack.pushPose();

        poseStack.translate(0.5F,0,0.5F);

        float[] c = {((color>>16)&0xFF)/256f, ((color>>8)&0xFF)/256f, (color&0xFF)/256f};
        BeaconRenderSettings settings = new BeaconRenderSettings(blockEntity, poseStack, multiBufferSource, partialTicks, time, baseHeight, height, c, ResourceLocation.fromNamespaceAndPath(BEAM_LOCATION.getNamespace(), BEAM_LOCATION.getPath()), 0.125F, 5);

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
                RenderUtils.renderTube(poseStack.last().pose(), multiBufferSource.getBuffer(settings.renderType), new Vector3f(-s1, settings.baseHeight*16, -s1), new Vector3f(s1, (settings.height + settings.baseHeight)*16, s1), settings.color[0], settings.color[1], settings.color[2], settings.alpha, 0, settings.time/5F, 1, height+(settings.time/5F));
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
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f1));
        matrixStackIn.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, PiSin, 0.0F, PiSin));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.scale(0.875F, 0.875F, 0.875F);
        matrixStackIn.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, PiSin, 0.0F, PiSin));
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f1));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_OUT)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.scale(0.875F, 0.875F, 0.875F);
        matrixStackIn.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, PiSin, 0.0F, PiSin));
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f1));
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.entitySmoothCutout(TEXTURE_IN)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        RenderUtils.renderPart(matrixStackIn.last().pose(), bufferIn.getBuffer(RenderType.eyes(TEXTURE_IN)), new Vector3f(-radius,-radius,-radius), new Vector3f(radius,radius,radius), red, green, blue, alpha);
        matrixStackIn.popPose();
    }

    public boolean shouldRenderOffScreen(BeaconBlockEntity blockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 256;
    }

    public boolean shouldRender(BeaconBlockEntity blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(cameraPos.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    public AABB getRenderBoundingBox(BeaconBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(pos.getX() - 3.0, pos.getY() - 3, pos.getZ() - 3, (double)pos.getX() + 3.0, 1024.0, (double)pos.getZ() + 3.0);
    }
}
