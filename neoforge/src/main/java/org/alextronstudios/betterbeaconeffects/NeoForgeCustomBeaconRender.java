package org.alextronstudios.betterbeaconeffects;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeaconBeamOwner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.IBlockEntityRendererExtension;

public class NeoForgeCustomBeaconRender<T extends BlockEntity & BeaconBeamOwner>
        extends CustomBeaconRender<T>
        implements IBlockEntityRendererExtension<T> {

    public NeoForgeCustomBeaconRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}