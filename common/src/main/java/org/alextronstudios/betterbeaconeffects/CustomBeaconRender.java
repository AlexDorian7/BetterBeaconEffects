package org.alextronstudios.betterbeaconeffects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.crystal.EndCrystalModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.client.renderer.entity.state.EndCrystalRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBeamOwner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffect;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconEffectRegistry;
import org.alextronstudios.betterbeaconeffects.beaconEffectApi.BeaconRenderSettings;
import org.alextronstudios.betterbeaconeffects.utils.RenderUtils;
import org.alextronstudios.betterbeaconeffects.utils.Utils;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class CustomBeaconRender<T extends BlockEntity & BeaconBeamOwner> implements BlockEntityRenderer<T, CustomBeaconRenderState> {
    public static final Identifier BEAM_LOCATION = Identifier.fromNamespaceAndPath("betterbeaconeffects", "textures/entity/beacon_beam_no_texture.png");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("betterbeaconeffects", "textures/misc/beacon.png");
    public static final int MAX_RENDER_Y = 2048;
    private static final float BEAM_SCALE_THRESHOLD = 96.0F;
    public static final float BEAM_GLOW_RADIUS = 0.125F;

    private final EndCrystalModel model;

    private final BlockEntityRendererProvider.Context context;

    public CustomBeaconRender(BlockEntityRendererProvider.Context context) {
        this.context = context;
        model = new EndCrystalModel(context.bakeLayer(ModelLayers.END_CRYSTAL));
    }

    @Override
    public CustomBeaconRenderState createRenderState() {
        return new CustomBeaconRenderState();
    }

    public void extractRenderState(
            T blockEntity, CustomBeaconRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        extract(blockEntity, state, partialTicks, cameraPosition);
    }

    public static <T extends BlockEntity & BeaconBeamOwner> void extract(T blockEntity, CustomBeaconRenderState state, float partialTicks, Vec3 cameraPosition) {
        state.animationTime = blockEntity.getLevel() != null ? Math.floorMod(blockEntity.getLevel().getGameTime(), 24000) + partialTicks : 0.0F;
        state.sections = blockEntity.getBeamSections().stream().map(section -> new BeaconRenderState.Section(section.getColor(), section.getHeight())).toList();
        float distanceToBeacon = (float)cameraPosition.subtract(Vec3.atCenterOf(state.blockPos)).horizontalDistance();
        LocalPlayer player = Minecraft.getInstance().player;
        state.beamRadiusScale = player != null && player.isScoping() ? 1.0F : Math.max(1.0F, distanceToBeacon / BEAM_SCALE_THRESHOLD);

        state.glassRendering = blockEntity.getLevel() != null && blockEntity.getLevel().getBlockState(blockEntity.getBlockPos().above()).is(Blocks.GLASS);
        state.blockEntity = blockEntity;
    }

    public void submit(CustomBeaconRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int beamStart = 0;

        Utils.gatherBlocks(state.blockEntity, poseStack, submitNodeCollector, state.activeEffects, state.glassRendering);


        // render nether star
        poseStack.pushPose();
        poseStack.translate(0.5, 0.125, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        EndCrystalRenderState endCrystalRenderState = new EndCrystalRenderState();
        endCrystalRenderState.ageInTicks = state.animationTime;
        endCrystalRenderState.showsBottom = false;
        RenderUtils.renderNetherStar(poseStack, submitNodeCollector, endCrystalRenderState, model, TEXTURE);
        poseStack.popPose();

        // render glass border
        if (state.glassRendering) {
            RenderUtils.renderLineCube(poseStack, submitNodeCollector, new Vector3f(-3), new Vector3f(4), 1, 1, 1, 1);
        }

        poseStack.pushPose();
        float[] colorArr = {0,0,0};
        BeaconRenderSettings settings = new BeaconRenderSettings(poseStack, submitNodeCollector, beamStart, 0, colorArr, BEAM_LOCATION, 0.125f, 0, context, state);
        for (Identifier activeEffect : state.activeEffects) {
            BeaconEffect beaconEffect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            beaconEffect.customRenderStep(settings);
            if (beaconEffect.hasCustomRender()) {
                beaconEffect.customRenderer(settings);
                poseStack.popPose();
                return;
            }
        }
        poseStack.popPose();

        for (int i = 0; i < state.sections.size(); i++) {
            BeaconRenderState.Section beamSection = state.sections.get(i);
            submitBeaconBeam(
                    poseStack,
                    submitNodeCollector,
                    state.beamRadiusScale,
                    state.animationTime,
                    beamStart,
                    i == state.sections.size() - 1 ? 2048 : beamSection.height(),
                    beamSection.color(),
                    state
            );
            beamStart += beamSection.height();
        }
    }

    private void submitBeaconBeam(
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float beamRadiusScale, float animationTime, int beamStart, int height, int color, CustomBeaconRenderState state
    ) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);

        float[] colorArr = {ARGB.red(color)/255f, ARGB.green(color)/255f, ARGB.blue(color)/255f};
        BeaconRenderSettings settings = new BeaconRenderSettings(poseStack, submitNodeCollector, beamStart, height, colorArr, BEAM_LOCATION, 0.125f, 5, context, state);
        for (Identifier activeEffect : state.activeEffects) {
            BeaconEffect beaconEffect = BeaconEffectRegistry.getRegistry().get(activeEffect);
            settings = beaconEffect.alterRenderer(settings);
        }

        submitBeaconBeam(settings,
                settings.poseStack, submitNodeCollector, 1.0F, settings.beaconRenderState.animationTime, settings.baseHeight, settings.height, BEAM_GLOW_RADIUS * beamRadiusScale
        );
        poseStack.popPose();
    }

    public void submitBeaconBeam(
            BeaconRenderSettings settings,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            float scale,
            float animationTime,
            int beamStart,
            int height,
            float beamGlowRadius
    ) {
        int beamEnd = beamStart + height;

        float scroll = height < 0 ? animationTime : -animationTime;
        float texVOff = Mth.frac(scroll * 0.2F - Mth.floor(scroll * 0.1F));

        for (int i=1; i<=settings.beams; i++) {
            float wnx = -beamGlowRadius*i;
            float wnz = -beamGlowRadius*i;
            float enx = beamGlowRadius*i;
            float enz = -beamGlowRadius*i;
            float wsx = -beamGlowRadius*i;
            float wsz = beamGlowRadius*i;
            float esx = beamGlowRadius*i;
            float esz = beamGlowRadius*i;
            float uu1 = 0.0F;
            float uu2 = 1.0F;
            float vv2 = -1.0F + texVOff;
            float vv1 = height * scale + vv2;
            int color = ARGB.colorFromFloat(settings.alpha, settings.color[0], settings.color[1], settings.color[2]);
            submitNodeCollector.submitCustomGeometry(
                    poseStack,
                    settings.renderType,
                    (pose, buffer) -> renderPart(pose, buffer, color, beamStart, beamEnd, wnx, wnz, enx, enz, wsx, wsz, esx, esz, 0.0F, 1.0F, vv1, vv2)
            );
        }
    }

    private static void renderPart(
            PoseStack.Pose pose,
            VertexConsumer builder,
            int color,
            int beamStart,
            int beamEnd,
            float wnx,
            float wnz,
            float enx,
            float enz,
            float wsx,
            float wsz,
            float esx,
            float esz,
            float uu1,
            float uu2,
            float vv1,
            float vv2
    ) {
        renderQuad(pose, builder, color, beamStart, beamEnd, wnx, wnz, enx, enz, uu1, uu2, vv1, vv2);
        renderQuad(pose, builder, color, beamStart, beamEnd, esx, esz, wsx, wsz, uu1, uu2, vv1, vv2);
        renderQuad(pose, builder, color, beamStart, beamEnd, enx, enz, esx, esz, uu1, uu2, vv1, vv2);
        renderQuad(pose, builder, color, beamStart, beamEnd, wsx, wsz, wnx, wnz, uu1, uu2, vv1, vv2);
    }

    private static void renderQuad(
            PoseStack.Pose pose,
            VertexConsumer builder,
            int color,
            int beamStart,
            int beamEnd,
            float wnx,
            float wnz,
            float enx,
            float enz,
            float uu1,
            float uu2,
            float vv1,
            float vv2
    ) {
        addVertex(pose, builder, color, beamEnd, wnx, wnz, uu2, vv1);
        addVertex(pose, builder, color, beamStart, wnx, wnz, uu2, vv2);
        addVertex(pose, builder, color, beamStart, enx, enz, uu1, vv2);
        addVertex(pose, builder, color, beamEnd, enx, enz, uu1, vv1);
    }

    private static void addVertex(PoseStack.Pose pose, VertexConsumer builder, int color, int y, float x, float z, float u, float v) {
        builder.addVertex(pose, x, y, z).setColor(color).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public int getViewDistance() {
        return Minecraft.getInstance().options.getEffectiveRenderDistance() * 16;
    }

    @Override
    public boolean shouldRender(T blockEntity, Vec3 cameraPosition) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(cameraPosition.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }


}
