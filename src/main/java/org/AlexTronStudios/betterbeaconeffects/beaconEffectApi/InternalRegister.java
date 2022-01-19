package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import net.minecraft.resources.ResourceLocation;
import org.AlexTronStudios.betterbeaconeffects.beaconEffects.*;

import java.util.function.Supplier;

public class InternalRegister {

    private static boolean registered = false;

    @Deprecated
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

        registered = true;
    }

    private static void register(String name, Supplier<BeaconEffect> effect) {
        BeaconEffectRegistry.register(new ResourceLocation("betterbeaconeffects", name), effect);
    }
}
