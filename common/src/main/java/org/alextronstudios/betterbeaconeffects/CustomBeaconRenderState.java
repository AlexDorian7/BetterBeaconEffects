package org.alextronstudios.betterbeaconeffects;

import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomBeaconRenderState extends BeaconRenderState {

    public List<Identifier> activeEffects = new ArrayList<>();
    public boolean glassRendering;
    public BlockEntity blockEntity;
}
