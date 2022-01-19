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
     * @param settings The settings object
     */
    default void customRenderer(BeaconRenderSettings settings) {}

    /**
     * This is called once each frame to do additional rendering
     * @param settings The settings for rendering
     */
    default void customRenderStep(BeaconRenderSettings settings) {}
}
