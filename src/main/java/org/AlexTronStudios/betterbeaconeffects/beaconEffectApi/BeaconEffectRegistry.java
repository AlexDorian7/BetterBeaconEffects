package org.AlexTronStudios.betterbeaconeffects.beaconEffectApi;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.AlexTronStudios.betterbeaconeffects.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BeaconEffectRegistry {
    private static final Map<ResourceLocation, BeaconEffect> registry = new Object2ObjectArrayMap<>();
    private static final List<Pair<ResourceLocation, Block>> blockRegistry = new ArrayList<>();

    public static Map<ResourceLocation, BeaconEffect> getRegistry() {
        return registry;
    }

    public static List<Pair<ResourceLocation, Block>> getBlockRegistry() {
        return blockRegistry;
    }

    /**
     * Used to register a new beacon beam effect
     * @param name The name to register the effect under
     * @param effect An instance of the effect class to register
     */
    @Deprecated
    public static void register(ResourceLocation name, BeaconEffect effect) {
        registry.put(name, effect);
        blockRegistry.add(new Pair(name, effect.getBlock()));
    }

    /**
     * Used to register a new beacon beam effect
     * @param name The name to register the effect under
     * @param effect A supplier of the effect class to register
     */
    public static void register(ResourceLocation name, Supplier<BeaconEffect> effect) {
        registry.put(name, effect.get());
        blockRegistry.add(new Pair(name, effect.get().getBlock()));
    }
}
