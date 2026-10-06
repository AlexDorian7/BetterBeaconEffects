package org.alextronstudios.betterbeaconeffects;

import org.alextronstudios.betterbeaconeffects.beaconEffectApi.InternalRegister;

public final class BetterBeaconEffects {
    public static final String MOD_ID = "betterbeaconeffects";

    private BetterBeaconEffects() {}

    public static void init() {
        InternalRegister.register();
    }
}
