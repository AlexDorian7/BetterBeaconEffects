package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

public interface BeaconEffect {
    String getName();
    Block getBlock();
    int getColor();
    BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings);
    default boolean hasCustomRender() {
        return false;
    }
    default void customRenderer(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, float[] color) {}
}
