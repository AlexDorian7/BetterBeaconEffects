package org.alextronstudios.betterbeaconeffects.beaconEffectApi;

import net.minecraft.resources.ResourceLocation;
import org.alextronstudios.betterbeaconeffects.beaconEffects.*;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

public class InternalRegister {

    private static boolean registered = false;

    @ApiStatus.Internal
    /**
     * Do not use. For internal Mod use only!
     * If you need to register an effect see: BeaconEffectRegistry$register
     */
    public static void register() {
        if (registered) return;

        register("debug_effect", DebugEffect::new);
        register("fading_effect", FadingEffect::new);
        register("rainbow_effect", RainbowEffect::new);
        register("remove_beam_effect", RemoveBeamEffect::new);
        register("add_beam_effect", AddBeamEffect::new);
        register("texture_effect", TextureEffect::new);
        register("spin_effect", SpinEffect::new);
        register("alpha_effect", AlphaEffect::new);
        register("movep_effect", MovePEffect::new);
        register("moven_effect", MoveNEffect::new);
        register("magic_effect", MagicEffect::new);
        register("laser_effect", LaserEffect::new);
        register("line_effect", LineEffect::new);
        register("color_fade_effect", ColorFadeEffect::new);
        register("nether_effect", NetherEffect::new);
        register("negate_effect", NegateEffect::new);
        register("colored_magic_effect", ColoredMagicEffect::new);
        register("air_effect", AirEffect::new);
        register("fire_effect", FireEffect::new);
        register("parallax_effect", ParallaxEffect::new);
        register("water_effect", WaterEffect::new);
        register("earth_effect", EarthEffect::new);

        registered = true;
    }

    private static void register(String name, Supplier<BeaconEffect> effect) {
        BeaconEffectRegistry.register(ResourceLocation.fromNamespaceAndPath("betterbeaconeffects", name), effect);
    }
}
