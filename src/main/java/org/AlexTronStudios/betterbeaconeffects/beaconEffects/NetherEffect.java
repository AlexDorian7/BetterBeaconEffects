package org.AlexTronStudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CryingObsidianBlock;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.AlexTronStudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;

import java.awt.*;
import java.util.Random;

public class NetherEffect implements BeaconEffect {

    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "Nether Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.NETHERRACK;
    }

    @Override
    public int getColor() {
        return new Color(255,127,0).getRGB();
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        float f = beaconRenderSettings.time + beaconRenderSettings.partialTicks;
        f=(f/100) % 1;
        float s = 0.5F*(Mth.sin((float) (2*Math.PI*f))) + 0.5F;
        float s2 = s/2;
        s2+=0.5f;
        s /= 6;
        Color c = Color.getHSBColor(s, 1, s2);
        beaconRenderSettings.color[0] = c.getRed()/256F;
        beaconRenderSettings.color[1] = c.getGreen()/256F;
        beaconRenderSettings.color[2] = c.getBlue()/256F;
        return beaconRenderSettings;
    }

    @Override
    public void customRenderStep(BeaconRenderSettings settings) {
        if (RANDOM.nextInt(16) == 0) {
            BlockPos pos = settings.blockEntity.getBlockPos();
            float x = RANDOM.nextFloat()*3+pos.getX()-1;
            float y = RANDOM.nextFloat()*3+pos.getY()-1;
            float z = RANDOM.nextFloat()*3+pos.getZ()-1;
            if (RANDOM.nextBoolean()) {
                settings.blockEntity.getLevel().addParticle(ParticleTypes.FLAME, x, y, z, 0, 0, 0);
            } else {
                settings.blockEntity.getLevel().addParticle(ParticleTypes.LAVA, x, y, z, 0, 0, 0);
            }
        }
    }
}
