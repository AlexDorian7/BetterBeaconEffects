package org.alextronstudios.betterbeaconeffects.beaconEffects;

import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.alextronstudios.betterbeaconeffects.CustomBeaconRender;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BetterBeaconRenderTypes;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.Random;

public class CubeEffect implements BeaconEffect {
    private static final long SEED = 1337;
    private static final int POINT_AMOUNT = 16;
    private static final float SPEED = 0.01f;
    private static final ArrayList<Vector3fc> POINTS = new ArrayList<>(POINT_AMOUNT);

    static {
        Random random = new Random(SEED);
        for (int i=0; i<POINT_AMOUNT; i++) {
            POINTS.add(new Vector3f(random.nextFloat()-0.5f, random.nextFloat(), random.nextFloat()-0.5f));
        }
    }

    @Override
    public String getName() {
        return "Cube Effect";
    }

    @Override
    public Block getBlock() {
        return Blocks.SULFUR;
    }

    @Override
    public int getColor() {
        return 0xFFFF00;
    }

    @Override
    public BeaconRenderSettings alterRenderer(BeaconRenderSettings beaconRenderSettings) {
        return beaconRenderSettings;
    }

    @Override
    public boolean hasCustomBeamRender() {
        return true;
    }

    @Override
    public void customBeamRenderer(BeaconRenderSettings settings, int sectionIndex) {




        settings.submitNodeCollector.submitCustomGeometry(settings.poseStack, settings.renderType, (pose, vertexConsumer) -> {
            RenderUtils.submitBeaconBeam(pose, vertexConsumer, settings.state.beamRadiusScale, settings.state.animationTime, settings.baseHeight, settings.height, 0.5f, 1, 0, 0, 0, 0.25f, true, true);

            for (Vector3fc point : POINTS) {
                Vector3f p = new Vector3f();
                point.get(p);
                p.mul(1,settings.height, 1);
                p.add(0, settings.state.animationTime/32*settings.height, 0, p);
                p.set(p.x, p.y%settings.height+settings.baseHeight, p.z);

                RenderUtils.renderParticlePoint(pose, vertexConsumer, p, settings.color[0], settings.color[1], settings.color[2], settings.alpha);
            }
        });
    }
}
