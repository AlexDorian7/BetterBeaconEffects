package org.alextronstudios.betterbeaconeffects.utils;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RenderUtils {

    private static final List<Triplet<Float, Float, Float>> vectorPairs = new ArrayList<>();
    private static final List<Pair<Integer, Integer>> lines = new ArrayList<>();
    private static boolean listLoaded = false;

    public static final float SIN_45 = (float)Math.sin((Math.PI / 4D));

    private static void addToList() {
        if (!listLoaded) {
            vectorPairs.add(new Triplet<>(-1/16F,-1/16F, -1/16F));
            vectorPairs.add(new Triplet<>(-1/16F, -1/16F,1/16F));
            vectorPairs.add(new Triplet<>(1/16F, 1/16F,1/16F));
            vectorPairs.add(new Triplet<>(1/16F, 1/16F,-1/16F));
            lines.add(new Pair<>(0,1));
            lines.add(new Pair<>(1,2));
            lines.add(new Pair<>(2,3));
            lines.add(new Pair<>(3,0));
            listLoaded = true;
        }
    }

    public static void renderPart(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        renderPart(stackIn, bufferIn, start, end, r, g, b, a, 0, 0, 1, 1);
    }

    public static void renderTube(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        renderTube(stackIn, bufferIn, start, end, r, g, b, a, 0, 0, 1, 1);
    }

    public static void renderPart(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a, int u1, int v1, int u2, int v2) {
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(end.z()), convert(end.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(start.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(end.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(start.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(start.y()), convert(start.z()), convert(start.z()), convert(end.z()), convert(end.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(end.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
    }

    public static void renderTube(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a, float u1, float v1, float u2, float v2) {
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(end.z()), convert(end.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(start.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(end.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
        renderFace(stackIn, bufferIn, convert(start.x()), convert(start.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2);
    }

    public static void renderLine3d(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float size, float r, float g, float b, float a) {
        renderLine3d(stackIn, bufferIn, start, end, size, r, g, b, a, 0, 0, 1, 1, 0, 1);
    }

    public static void renderLine3d(Matrix4f stackIn, VertexConsumer bufferIn, Vector3f start, Vector3f end, float size, float r, float g, float b, float a, float u, float v, float u1, float v1, int u2, int v2) {
        addToList();
        for (Pair<Integer, Integer> line : lines) {
            Vector3f st = new Vector3f(start.x()+vectorPairs.get(line.first).first*size, start.y()+vectorPairs.get(line.first).second*size, start.z()+vectorPairs.get(line.first).third*size);
            Vector3f nd = new Vector3f(end.x()+vectorPairs.get(line.first).first*size, end.y()+vectorPairs.get(line.first).second*size, end.z()+vectorPairs.get(line.first).third*size);
            Vector3f th = new Vector3f(start.x()+vectorPairs.get(line.second).first*size, start.y()+vectorPairs.get(line.second).second*size, start.z()+vectorPairs.get(line.second).third*size);
            Vector3f rd = new Vector3f(end.x()+vectorPairs.get(line.second).first*size, end.y()+vectorPairs.get(line.second).second*size, end.z()+vectorPairs.get(line.second).third*size);
            RenderUtils.renderFace(stackIn, bufferIn, st, nd, rd, th, r, g, b, a, u, v, u1, v1, u2, v2);
        }
    }

    public static void renderLine(PoseStack poseStack, MultiBufferSource multiBufferSource, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        VertexConsumer bufferIn = multiBufferSource.getBuffer(RenderType.LINES);

        Matrix4f stackIn = poseStack.last().pose();
        
        bufferIn.addVertex(stackIn, start.x(), start.y(), start.z()).setColor(r,g,b,a).setNormal(poseStack.last(), 1,0,0);
        bufferIn.addVertex(stackIn, end.x(), end.y(), end.z()).setColor(r,g,b,a).setNormal(poseStack.last(), 1,0,0);
    }

    public static float convert(float in) {
        return in / 16F;
    }

    public static float getY(long time, float partialTicks) {
        float f = (float)time + partialTicks;
        float f1 = (float) Math.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }

    public static void renderFace(Matrix4f matrix4f, VertexConsumer iVertexBuilder, float startX, float endX, float startY, float endY, float p_228884_8_, float p_228884_9_, float p_228884_10_, float p_228884_11_, float r, float g, float b, float a, int u1, int v1, int u2, int v2) {
        iVertexBuilder.addVertex(matrix4f, startX, startY, p_228884_8_).setColor(r, g, b, a).setUv(u1,v1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(1,1).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, endX, startY, p_228884_9_).setColor(r, g, b, a).setUv(u2,v1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(1,1).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, endX, endY, p_228884_10_).setColor(r, g, b, a).setUv(u2,v2).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(1,1).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, startX, endY, p_228884_11_).setColor(r, g, b, a).setUv(u1,v2).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(1,1).setNormal(0,1,0);
    }

    public static void renderFace(Matrix4f matrix4f, VertexConsumer iVertexBuilder, float startX, float endX, float startY, float endY, float p_228884_8_, float p_228884_9_, float p_228884_10_, float p_228884_11_, float r, float g, float b, float a, float u1, float v1, float u2, float v2) {
        iVertexBuilder.addVertex(matrix4f, startX, startY, p_228884_8_).setColor(r, g, b, a).setUv(u1,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, endX, startY, p_228884_9_).setColor(r, g, b, a).setUv(u2,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, endX, endY, p_228884_10_).setColor(r, g, b, a).setUv(u2,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0,1,0);
        iVertexBuilder.addVertex(matrix4f, startX, endY, p_228884_11_).setColor(r, g, b, a).setUv(u1,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0,1,0);
    }

    public static void renderFace(Matrix4f matrix4f, VertexConsumer vertexConsumer, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float r, float g, float b, float a, float u, float v, float u0, float v0, int i, int i1) {
        vertexConsumer.addVertex(matrix4f, v1.x(), v1.y(), v1.z()).setColor(r,g,b,a).setUv(u,v).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(i,i1).setNormal(0,1,0);
        vertexConsumer.addVertex(matrix4f, v2.x(), v2.y(), v2.z()).setColor(r,g,b,a).setUv(u0,v).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(i,i1).setNormal(0,1,0);
        vertexConsumer.addVertex(matrix4f, v3.x(), v3.y(), v3.z()).setColor(r,g,b,a).setUv(u0,v0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(i,i1).setNormal(0,1,0);
        vertexConsumer.addVertex(matrix4f, v4.x(), v4.y(), v4.z()).setColor(r,g,b,a).setUv(u,v0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(i,i1).setNormal(0,1,0);
    }

    private static void addVertex(PoseStack.Pose pose, VertexConsumer consumer, int color, int y, float x, float z, float u, float v) {
        consumer.addVertex(pose, x, (float)y, z).setColor(color).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    public static float xOffset(float f) {
        return f * 0.1F;
    }

    public static float yOffset(float f) {
        return f * 0.01F;
    }
}
