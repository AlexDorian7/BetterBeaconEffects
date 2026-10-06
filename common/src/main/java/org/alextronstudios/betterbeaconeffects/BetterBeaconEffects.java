package org.alextronstudios.betterbeaconeffects;

import org.alextronstudios.betterbeaconeffects.beaconEffectApi.InternalRegister;

public final class BetterBeaconEffects {
    private BetterBeaconEffects() {}

    public static void init() {
        InternalRegister.register();
    }
}
