package org.alextronstudios.betterbeaconeffects.utils;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderUtils {

    public static void renderPart(PoseStack.Pose pose, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        renderPart(pose, bufferIn, start, end, r, g, b, a, 0, 0, 1, 1);
    }

    public static void renderTubeVortex(PoseStack.Pose pose, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        renderTubeVortex(pose, bufferIn, start, end, r, g, b, a, 0, 0, 1, 1);
    }

    public static void renderPart(PoseStack.Pose pose, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a, int u1, int v1, int u2, int v2) {
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(end.z()), convert(end.z()), r, g, b, a, u1, v1, u2, v2, 0, 0, 1); // south
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(start.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, 0, 0, -1); // north
        renderFace(pose, bufferIn, convert(end.x()), convert(end.x()), convert(end.y()), convert(start.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, 1, 0, 0); // east
        renderFace(pose, bufferIn, convert(start.x()), convert(start.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, -1, 0, 0); // west
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(start.y()), convert(start.z()), convert(start.z()), convert(end.z()), convert(end.z()), r, g, b, a, u1, v1, u2, v2, 0, -1, 0); // down
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(end.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, 0, 1, 0); // up
    }

    public static void renderTubeVortex(PoseStack.Pose pose, VertexConsumer bufferIn, Vector3f start, Vector3f end, float r, float g, float b, float a, float u1, float v1, float u2, float v2) {
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(end.z()), convert(end.z()), convert(end.z()), convert(end.z()), r, g, b, a, u2, v2, u1, v1, 0, 0, 1); // south
        renderFace(pose, bufferIn, convert(start.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(start.z()), convert(start.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, 0, 0, -1); // north
        renderFace(pose, bufferIn, convert(end.x()), convert(end.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u1, v1, u2, v2, 1, 0, 0); // east
        renderFace(pose, bufferIn, convert(start.x()), convert(start.x()), convert(start.y()), convert(end.y()), convert(start.z()), convert(end.z()), convert(end.z()), convert(start.z()), r, g, b, a, u2, v2, u1, v1, -1, 0, 0); // west
    }

    public static void renderTubePoly(PoseStack.Pose pose, VertexConsumer vertexConsumer, int sides, float radius, float baseHeight, float height, float r, float g, float b, float a) {
        for (int i=0; i<sides; i++) {
            float alpha = (float) i / sides;
            float alpha1 = (float) (i+1) / sides;
            float theta = alpha * Mth.TWO_PI;
            float theta1 = alpha1 * Mth.TWO_PI;
            float x1 = Mth.cos(theta);
            float y1 = Mth.sin(theta);
            float x2 = Mth.cos(theta1);
            float y2 = Mth.sin(theta1);

            vertexConsumer.addVertex(pose, x1*radius, baseHeight, y1*radius)          .setUv(alpha , 0) .setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, x1, 0, y1);
            vertexConsumer.addVertex(pose, x1*radius, baseHeight+height, y1*radius).setUv(alpha , height).setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, x1, 0, y1);
            vertexConsumer.addVertex(pose, x2*radius, baseHeight+height, y2*radius).setUv(alpha1, height).setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, x2, 0, y2);
            vertexConsumer.addVertex(pose, x2*radius, baseHeight, y2*radius)          .setUv(alpha1, 0) .setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, x2, 0, y2);
        }
    }

    public static void renderTubePolyTrap(PoseStack.Pose pose, VertexConsumer vertexConsumer, int sides, float radiusl, float radiush, float baseHeight, float height, float r, float g, float b, float a) {
        renderTubePolyTrap(pose, vertexConsumer, sides, radiusl, radiush, baseHeight, height, r, g, b, a, 0, 0);
    }


    public static void renderTubePolyTrap(PoseStack.Pose pose, VertexConsumer vertexConsumer, int sides, float radiusl, float radiush, float baseHeight, float height, float r, float g, float b, float a, float u, float v) {
        for (int i=0; i<sides; i++) {
            float alpha = (float) i / sides;
            float alpha1 = (float) (i+1) / sides;
            float theta = alpha * Mth.TWO_PI;
            float theta1 = alpha1 * Mth.TWO_PI;
            float x1 = Mth.cos(theta);
            float y1 = Mth.sin(theta);
            float x2 = Mth.cos(theta1);
            float y2 = Mth.sin(theta1);

            float slope = height / (radiush - radiusl);
            float perp = -1 / slope;
            Vector3f normal1 = new Vector3f(x1, perp, y1);
            Vector3f normal2 = new Vector3f(x2, perp, y2);
            normal1.normalize();
            normal2.normalize();

            vertexConsumer.addVertex(pose, x1*radiusl, baseHeight, y1*radiusl)          .setUv(alpha+u , v)           .setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, normal1.x, normal1.y, normal1.z);
            vertexConsumer.addVertex(pose, x1*radiush, baseHeight+height, y1*radiush).setUv(alpha+u , height+v).setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, normal1.x, normal1.y, normal1.z);
            vertexConsumer.addVertex(pose, x2*radiush, baseHeight+height, y2*radiush).setUv(alpha1+u, height+v).setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, normal2.x, normal2.y, normal2.z);
            vertexConsumer.addVertex(pose, x2*radiusl, baseHeight, y2*radiusl)          .setUv(alpha1+u, v)           .setColor(r, g, b, a).setLight(15728880).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(pose, normal2.x, normal2.y, normal2.z);
        }
    }

    public static void renderLineCube(PoseStack.Pose pose, MultiBufferSource multiBufferSource, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        Vector3f ca = start;
        Vector3f cb = new Vector3f(start.x, start.y, end.z);
        Vector3f cc = new Vector3f(end.x, start.y, end.z);
        Vector3f cd = new Vector3f(end.x, start.y, start.z);
        Vector3f ce = new Vector3f(start.x, end.y, start.z);
        Vector3f cf = new Vector3f(start.x, end.y, end.z);
        Vector3f cg = end;
        Vector3f ch = new Vector3f(end.x, end.y, start.z);

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.lines());
        renderLine(pose, vertexConsumer, ca, cb, r, g, b, a); // bottom
        renderLine(pose, vertexConsumer, cb, cc, r, g, b, a);
        renderLine(pose, vertexConsumer, cc, cd, r, g, b, a);
        renderLine(pose, vertexConsumer, cd, ca, r, g, b, a);

        renderLine(pose, vertexConsumer, ce, cf, r, g, b, a); // top
        renderLine(pose, vertexConsumer, cf, cg, r, g, b, a);
        renderLine(pose, vertexConsumer, cg, ch, r, g, b, a);
        renderLine(pose, vertexConsumer, ch, ce, r, g, b, a);

        renderLine(pose, vertexConsumer, ca, ce, r, g, b, a); // sides
        renderLine(pose, vertexConsumer, cb, cf, r, g, b, a);
        renderLine(pose, vertexConsumer, cc, cg, r, g, b, a);
        renderLine(pose, vertexConsumer, cd, ch, r, g, b, a);
    }

    private static void renderLine(PoseStack.Pose pose, VertexConsumer vertexConsumer, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        float endX = end.x - start.x;
        float endY = end.y - start.y;
        float endZ = end.z - start.z;
        vertexConsumer.addVertex(pose, start.x(), start.y(), start.z()).setColor(r,g,b,a).setNormal(pose, endX, endY, endZ);
        vertexConsumer.addVertex(pose, end.x(), end.y(), end.z()).setColor(r,g,b,a).setNormal(pose, -endX, -endY, -endZ);
    }

    public static void renderLine(PoseStack poseStack, MultiBufferSource multiBufferSource, Vector3f start, Vector3f end, float r, float g, float b, float a) {
        VertexConsumer bufferIn = multiBufferSource.getBuffer(RenderType.lines());
        Matrix4f stackIn = poseStack.last().pose();

        float endX = end.x - start.x;
        float endY = end.y - start.y;
        float endZ = end.z - start.z;

        bufferIn.addVertex(stackIn, start.x(), start.y(), start.z()).setColor(r,g,b,a).setNormal(poseStack.last(), endX, endY, endZ);
        bufferIn.addVertex(stackIn, end.x(), end.y(), end.z()).setColor(r,g,b,a).setNormal(poseStack.last(), -endX,-endY,-endZ);
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

    public static void renderFace(PoseStack.Pose pose, VertexConsumer vertexConsumer, float startX, float endX, float startY, float endY, float z1, float z2, float z3, float z4, float r, float g, float b, float a, float u1, float v1, float u2, float v2, float nx, float ny, float nz) {
        vertexConsumer.addVertex(pose, startX, startY, z1).setColor(r, g, b, a).setUv(u1,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, -nx, -ny, -nz); // Forward Face
        vertexConsumer.addVertex(pose, endX, startY, z2)  .setColor(r, g, b, a).setUv(u2,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, -nx, -ny, -nz);
        vertexConsumer.addVertex(pose, endX, endY, z3)    .setColor(r, g, b, a).setUv(u2,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, -nx, -ny, -nz);
        vertexConsumer.addVertex(pose, startX, endY, z4)  .setColor(r, g, b, a).setUv(u1,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, -nx, -ny, -nz);

        vertexConsumer.addVertex(pose, startX, startY, z1).setColor(r, g, b, a).setUv(u1,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, nx, ny, nz); // Backward Face
        vertexConsumer.addVertex(pose, startX, endY, z4)  .setColor(r, g, b, a).setUv(u1,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, nx, ny, nz);
        vertexConsumer.addVertex(pose, endX, endY, z3)    .setColor(r, g, b, a).setUv(u2,v2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, nx, ny, nz);
        vertexConsumer.addVertex(pose, endX, startY, z2)  .setColor(r, g, b, a).setUv(u2,v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, nx, ny, nz);
    }
}
