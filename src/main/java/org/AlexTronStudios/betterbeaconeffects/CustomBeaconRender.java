package org.AlexTronStudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffectRegistry;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.AlexTronStudios.betterbeaconeffects.utils.Pair;
import org.AlexTronStudios.betterbeaconeffects.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomBeaconRender implements BlockEntityRenderer<BeaconBlockEntity> {

    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam_no_texture.png");
    public static final int MAX_RENDER_Y = 1024;

    public CustomBeaconRender(BlockEntityRendererProvider.Context p_173529_) {
    }

    @Override
    public void render(BeaconBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int overlayIn) {
        long i = blockEntity.getLevel().getGameTime();

        List<BeaconBlockEntity.BeaconBeamSection> list = blockEntity.getBeamSections();
        int j = 0;

        for(int k = 0; k < list.size(); ++k) {
            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            renderBeaconBeam(blockEntity, poseStack, multiBufferSource, partialTicks, i, j, k == list.size() - 1 ? MAX_RENDER_Y : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
            j += beaconblockentity$beaconbeamsection.getHeight();
        }
    }

    public static void renderBeaconBeam(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, float[] color) {

        poseStack.pushPose();

        poseStack.translate(0,0,0);

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
        float[] c = {color[0], color[1], color[2]};
        BeaconRenderSettings settings = new BeaconRenderSettings(blockEntity, poseStack, multiBufferSource, partialTicks, time, baseHeight, height, c, new ResourceLocation(BEAM_LOCATION.getNamespace(), BEAM_LOCATION.getPath()), 0.125F, 4);

        for (ResourceLocation activeEffect : activeEffects) {
            BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            if (effect.hasCustomRender()) {
                renderer = effect;
            }
            effect.alterRenderer(settings);
        }

        if (renderer != null) {
            renderer.customRenderer(settings.blockEntity, settings.poseStack, settings.multiBufferSource, settings.partialTicks, settings.time, settings.baseHeight, settings.height, settings.color);
        } else {
            for (int i=0; i<settings.beams; i++) {
                float s = i*4+4;
                float s1 = s/2;
                RenderUtils.renderTube(poseStack.last().pose(), multiBufferSource.getBuffer(RenderType.beaconBeam(settings.texture, true)), new Vector3f(8-s1, settings.baseHeight*16, 8-s1), new Vector3f(8+s1, settings.height*16, 8+s1), settings.color[0], settings.color[1], settings.color[2], settings.alpha);
            }
        }

        poseStack.popPose();
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
