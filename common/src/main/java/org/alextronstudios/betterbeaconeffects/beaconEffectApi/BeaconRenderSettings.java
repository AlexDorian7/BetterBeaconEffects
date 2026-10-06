package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.alextronstudios.betterbeaconeffects.CustomBeaconRenderState;

public class BeaconRenderSettings {
    public PoseStack poseStack;
    public SubmitNodeCollector submitNodeCollector;
    public int height;
    public int baseHeight;
    public float[] color;
    public Identifier texture;
    public float alpha;
    public int beams;
    public RenderType renderType;
    public final BlockEntityRendererProvider.Context context;
    public CustomBeaconRenderState beaconRenderState;

    public BeaconRenderSettings(PoseStack poseStack, SubmitNodeCollector multiBufferSource, int baseHeight, int height, float[] color, Identifier texture, float alpha, int beams, BlockEntityRendererProvider.Context context, CustomBeaconRenderState beaconRenderState) {
        this.poseStack = poseStack;
        this.submitNodeCollector = multiBufferSource;
        this.height = height;
        this.baseHeight = baseHeight;
        this.color = color;
        this.texture = texture;
        this.alpha = alpha;
        this.beams = beams;
        this.context = context;
        this.renderType = RenderTypes.beaconBeam(this.texture, true);
        this.beaconRenderState = beaconRenderState;
    }

    /**
     * This should be done after the texture resource location is changed and a custom RenderType is not being used
     */
    public void recalculateRenderType() {
        this.renderType = RenderTypes.beaconBeam(this.texture, true);
    }
}
