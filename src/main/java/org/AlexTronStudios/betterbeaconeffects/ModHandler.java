package org.alextronstudios.betterbeaconeffects;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.InternalRegister;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betterbeaconeffects")
public class ModHandler
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "betterbeaconeffects";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModHandler(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(BetterBeaconRenderTypes.getInstance()::registerShaders);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.warn("Better Beacon Effects Loading...");
        InternalRegister.register();
        BlockEntityRenderers.register(BlockEntityType.BEACON, CustomBeaconRender::new);
        LOGGER.warn("Better Beacon Effects Loaded and Injected!");
    }

}
