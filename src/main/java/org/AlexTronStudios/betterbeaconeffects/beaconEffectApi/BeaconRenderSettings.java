package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

public class BeaconRenderSettings {
    public BeaconBlockEntity blockEntity;
    public PoseStack poseStack;
    public MultiBufferSource multiBufferSource;
    public float partialTicks;
    public long time;
    public int height;
    public int baseHeight;
    public float[] color;
    public ResourceLocation texture;
    public float alpha;
    public int beams;

    public BeaconRenderSettings(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, float[] color, ResourceLocation texture, float alpha, int beams) {
        this.blockEntity = blockEntity;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.partialTicks = partialTicks;
        this.time = time;
        this.height = height;
        this.baseHeight = baseHeight;
        this.color = color;
        this.texture = texture;
        this.alpha = alpha;
        this.beams = beams;
    }
}
