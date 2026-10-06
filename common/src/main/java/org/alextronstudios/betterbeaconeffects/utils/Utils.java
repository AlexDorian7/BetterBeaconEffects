package org.alextronstudios.betterbeaconeffects.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffectRegistry;

import java.util.List;

public class Utils {

    public static void gatherBlocks(BlockEntity blockEntity, List<Pair<Identifier, Block>> blocks, List<Identifier> activeEffects, boolean glassRendering) {
        for (int x = -3; x < 4; x++) {
            for (int y = -3; y < 4; y++) {
                for (int z = -3; z < 4; z++) {
                    for (Pair<Identifier, Block> block : blocks) {
                        BlockPos pos = blockEntity.getBlockPos().offset(x,y,z);
                        if (blockEntity.getLevel().getBlockState(pos).getBlock().equals(block.second)) {
                            activeEffects.add(block.first);
//                            if (glassRendering) {
//                                BeaconEffect effect = BeaconEffectRegistry.getRegistry().get(block.first);
//                                RenderUtils.renderLineCube(poseStack.last(), multiBufferSource, new Vector3f(x-0.001f, y-0.001f, z-0.001f), new Vector3f(x+1.001f, y+1.001f, z+1.001f), ((effect.getColor()>>16)&0xFF)/256F,((effect.getColor()>>8)&0xFF)/256F, (effect.getColor()&0xFF)/256F, 1);
//                            }
                        }
                    }
                }
            }
        }
    }
}
