package org.alextronstudios.betterbeaconeffects;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.InternalRegister;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

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
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.warn("Better Beacon Effects Loading...");
        InternalRegister.register();
        BlockEntityRenderers.register(BlockEntityType.BEACON, CustomBeaconRender::new);
        LOGGER.warn("Better Beacon Effects Loaded and Injected!");
    }
}
