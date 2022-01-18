package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import net.minecraft.resources.ResourceLocation;
import org.AlexTronStudios.betterbeaconeffects.beaconEffects.*;

public class InternalRegister {

    private static boolean registered = false;

    @Deprecated
    /**
     * Do not use. For internal Mod use only!
     * If you need to register an effect see: BeaconEffectRegistry$register
     */
    public static void register() {
        if (registered) return;

        register("debug_effect", new DebugEffect());
        register("fading_effect", new FadingEffect());
        register("rainbow_effect", new RainbowEffect());
        register("remove_beam_effect", new RemoveBeamEffect());
        register("add_beam_effect", new AddBeamEffect());
        register("texture_effect", new TextureEffect());
        register("spin_effect", new SpinEffect());
        register("alpha_effect", new AlphaEffect());
        register("movep_effect", new MovePEffect());
        register("moven_effect", new MoveNEffect());
        register("magic_effect", new MagicEffect());
        register("laser_effect", new LaserEffect());

        registered = true;
    }

    private static void register(String name, BeaconEffect effect) {
        BeaconEffectRegistry.register(new ResourceLocation("betterbeaconeffects", name), effect);
    }
}
