package org.alextronstudios.betterbeaconeffects;

import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.alextronstudios.betterbeaconeffects.utils.Pair;

import java.util.List;

public class CustomBeaconRenderState extends BeaconRenderState {

    public List<Pair<Identifier, Block>> blocks;
    public List<Identifier> activeEffects;
    public boolean glassRendering;
    public BlockEntity blockEntity;
}
