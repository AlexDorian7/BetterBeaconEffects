package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;

public interface BeaconEffect {
    /**
     * Returns the name of the effect
     * @return name
     */
    String getName();

    /**
     * Returns the block used to trigger the effect
     * @return block
     */
    Block getBlock();

    /**
     * Returns the color associated with the effect (currently unused)
     * @return color
     */
    int getColor();

    /**
     * Function called to alter the way the beam is rendered
     * @param beaconRenderSettings The input settings
     * @return The output settings
     */
    BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings);

    /**
     * Does this effect have a custom renderer that should be used?
     * @return boolean
     */
    default boolean hasCustomRender() {
        return false;
    }

    /**
     * The custom renderer to be used if hasCustomRender is true
     * @param blockEntity The Beacon Block Entity being rendered
     * @param poseStack The PoseStack
     * @param multiBufferSource The MultiBufferSource
     * @param partialTicks The partial ticks
     * @param time The current world time
     * @param baseHeight The starting height for the beam segment
     * @param height The height of the beam segment
     * @param color The color of the beam segment
     */
    default void customRenderer(BeaconBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, float partialTicks, long time, int baseHeight, int height, float[] color) {}
}
