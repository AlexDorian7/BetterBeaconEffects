package org.AlextronStudios.BetterBeaconEffects;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

public class OldBeaconTileEntityRender extends BeaconTileEntityRenderer {
	
	public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");
	public static final ResourceLocation TEXTURE_BEACON_BEAM_NO_TEXTURE = new ResourceLocation(
			"textures/entity/beacon_beam_no_texture.png");
	public static final ResourceLocation TEXTURE_SELECTION = new ResourceLocation("textures/misc/selection.png");
	public static final ResourceLocation TEXTURE_OUT = new ResourceLocation("textures/misc/beacon_out.png");
	public static final ResourceLocation TEXTURE_IN = new ResourceLocation("textures/misc/beacon_in.png");
	
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
		return RenderType.getEndPortal(p_228882_0_ + 1);
	}).collect(ImmutableList.toImmutableList());

	
	private static final float PiSin = (float) Math.sin((Math.PI / 4D));
	
	private static int beams = 4;
	private static boolean spinningBeam = false;
	private static boolean texture = false;
	private static boolean rainbow = false;
	private static boolean flash = false;
	private static boolean rotating = false;
	private static boolean debug = false;
	private static boolean selection = false;
	private static boolean magic = false;
	private static boolean background = false;
	private static float alpha = 0.125F;
	private static int offset = 0;
	
	public OldBeaconTileEntityRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	@Override
	@OnlyIn(Dist.CLIENT)
	public void render(BeaconTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		long i = tileEntityIn.getWorld().getGameTime();
		List<BeaconTileEntity.BeamSegment> list = tileEntityIn.getBeamSegments();
		int j = 0;

		beams = 4;
		spinningBeam = false;
		texture = false;
		rainbow = false;
		flash = false;
		rotating = false;
		debug = false;
		selection = false;
		magic = false;
		background = false;
		alpha = 0.125F;
		offset = 0;
		
		if (checkBlock(tileEntityIn, 0, 1, 0, Blocks.GLASS)) {
			selection = true;
			int yOffset = -3;
			float glowRadius = 3.5f;
			float glowAmount = flash((double) i, (double) partialTicks);
			int height = 7;
			float textureScale = 0.2F;
			float[] colors = { 1F, 1F, 1F };
			int i1 = yOffset + height;
			float f = (float) Math.floorMod(i, 40L) + partialTicks;
			float f1 = height < 0 ? f : -f;
			float f2 = MathHelper.frac(f1 * 0.2F - (float) MathHelper.floor(f1 * 0.1F));
			float f3 = colors[0];
			float f4 = colors[1];
			float f5 = colors[2];
			float f6 = -glowRadius;
			float f7 = -glowRadius;
			float f8 = -glowRadius;
			float f9 = -glowRadius;
			float f15 = -1.0F + f2;
			float f16 = (float) height * textureScale + f15;
			renderCube(matrixStackIn, bufferIn.getBuffer(RenderType.getBeaconBeam(TEXTURE_SELECTION, true)), f3, f4, f5,
					glowAmount, yOffset, i1, -f6 + 0.5F, -f7 + 0.5F, -glowRadius + 0.5F, -f8 + 0.5F, -f9 + 0.5F,
					-glowRadius + 0.5F, -glowRadius + 0.5F, -glowRadius + 0.5F, 0.0F, 1.0F, 1.0F, 0.0F);
		}
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				for (int z = -3; z < 4; z++) {
					if (checkBlock(tileEntityIn, x, y, z, Blocks.COMMAND_BLOCK)) {
						debug = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.COMMAND_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.PURPUR_BLOCK)) {
						flash = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.PURPUR_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.QUARTZ_BLOCK)) {
						spinningBeam = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.QUARTZ_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.OBSIDIAN)) {
						rainbow = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.OBSIDIAN, x, y, z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.OBSERVER)) {
						texture = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.OBSERVER, x, y, z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.SLIME_BLOCK)) {
						beams++;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.SLIME_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.REDSTONE_BLOCK)) {
						beams--;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.REDSTONE_BLOCK, x,
									y, z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.SEA_LANTERN)) {
						rotating = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.SEA_LANTERN, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.PRISMARINE)) {
						alpha = alpha + 0.125F;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.PRISMARINE, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.LAPIS_BLOCK)) {
						offset++;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.LAPIS_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.COAL_BLOCK)) {
						offset--;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.COAL_BLOCK, x, y,
									z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.END_STONE)) {
						magic = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.END_STONE, x,
									y, z);
					}
					if (checkBlock(tileEntityIn, x, y, z, Blocks.END_STONE_BRICKS)) {
						background = true;
						if (selection)
							renderSelectionBlock(tileEntityIn, matrixStackIn, bufferIn, BlockColors.END_STONE_BRICKS, x,
									y, z);
					}
				}
			}
		}
		renderNetherStar(matrixStackIn, bufferIn, 0.25F, i, partialTicks);
		for (int k = 0; k < list.size(); ++k) {
			BeaconTileEntity.BeamSegment beacontileentity$beamsegment = list.get(k);
			float[] color = beacontileentity$beamsegment.getColors();
			if (rainbow) {
				color = generateRainbow((double) i, (double) partialTicks);
			}
			if (flash) {
				alpha = flash((double) i, (double) partialTicks);
			}
			double d0 = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
			int l = this.getPasses(d0);
			renderBeamSegment(matrixStackIn, bufferIn, TEXTURE_BEACON_BEAM, TEXTURE_BEACON_BEAM_NO_TEXTURE,
					partialTicks, 1.0F, i, j, k == list.size() - 1 ? 1024 : beacontileentity$beamsegment.getHeight(),
					color, 0.2F, 0.125F, alpha, l);
			j += beacontileentity$beamsegment.getHeight();
		}
	}
	
	public static float[] generateRainbow(double totalWorldTime, double partialTicks) {
    	double d0 = (totalWorldTime % 36000) + partialTicks;
    	d0 = d0 / 100;
    	int colorEncode = Color.HSBtoRGB((float)d0, 1F, 1F);
    	int i1 = (colorEncode & 16711680) >> 16;
        int j1 = (colorEncode & 65280) >> 8;
        int k1 = (colorEncode & 255) >> 0;
        return new float[] {(float)i1 / 255.0F, (float)j1 / 255.0F, (float)k1 / 255.0F};
    }
    public static boolean checkBlock(BeaconTileEntity te, int rx, int ry, int rz, Block block) {
    	BlockPos pos = te.getPos().add(rx, ry, rz);
    	Block blockToCheck = te.getWorld().getBlockState(pos).getBlock();
    	
    	if (blockToCheck == block) {
    		return true;
    	}
    	return false;
    }
    public static float flash(double time, double partialTicks) {
    	float f = (float) (time + partialTicks);
    	float f1 = (float) (Math.sin((double)f * 0.05D) / 2D + 0.5D);
        return f1 / 1.3F + 0.1153846153846153846153846F;
    }

    public static void renderBeamSegment(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			ResourceLocation textureLocation, ResourceLocation textureLocation2, float partialTicks, float textureScale,
			long totalWorldTime, int yOffset, int height, float[] colors, float beamRadius, float glowRadius,
			float glowAmount, int passes) {
		int i = yOffset + height;
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.0D, 0.5D);
		float f = (float) Math.floorMod(totalWorldTime, 160L) + partialTicks;
		float f1 = height < 0 ? f : -f;
		float f2 = MathHelper.frac(f1 * 0.2F - (float) MathHelper.floor(f1 * 0.1F));
		float f3 = colors[0];
		float f4 = colors[1];
		float f5 = colors[2];
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f * 2.25F - 45.0F));
		float f6 = 0.0F;
		float f8 = 0.0F;
		float f9 = -beamRadius;
		float f10 = 0.0F;
		float f11 = 0.0F;
		float f12 = -beamRadius;
		float f13 = 0.0F;
		float f14 = 1.0F;
		float f15 = -1.0F + f2;
		float f16 = (float) height * textureScale * (0.5F / beamRadius) + f15;
		if (debug) {
			matrixStackIn.translate(f3, f4, f5);
		}
		if (spinningBeam) {
			if (!magic) {
				if (!texture) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RenderType.getBeaconBeam(textureLocation2, false)), f3,
							f4, f5, 1.0F, yOffset, i, 0.0F + offset, beamRadius + offset, beamRadius + offset,
							0.0F + offset, f9 + offset, 0.0F + offset, 0.0F + offset, f12 + offset, 0.0F + offset,
							1.0F + offset, f16 + offset, f15 + offset);
				} else {
					renderPart(matrixStackIn, bufferIn.getBuffer(RenderType.getBeaconBeam(textureLocation, false)), f3,
							f4, f5, 1.0F, yOffset, i, 0.0F + offset, beamRadius + offset, beamRadius + offset,
							0.0F + offset, f9 + offset, 0.0F + offset, 0.0F + offset, f12 + offset, 0.0F + offset,
							1.0F + offset, f16 + offset, f15 + offset);
				}
			} else {
				if (background) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0F, 0F, 0F, 1.0F, yOffset, i,
							0.0F + offset, beamRadius + offset, beamRadius + offset, 0.0F + offset, f9 + offset,
							0.0F + offset, 0.0F + offset, f12 + offset, 0.0F + offset, 1.0F + offset, f16 + offset,
							f15 + offset);
				}
				for (int l = 1; l < passes; l++) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RENDER_TYPES.get(l)), f3, f4, f5, 1.0F, yOffset, i,
							0.0F + offset, beamRadius + offset, beamRadius + offset, 0.0F + offset, f9 + offset,
							0.0F + offset, 0.0F + offset, f12 + offset, 0.0F + offset, 1.0F + offset, f16 + offset,
							f15 + offset);
				}
			}
		}
		matrixStackIn.pop();
		matrixStackIn.push();
		if (rotating)
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees((1*f) * 2.25F - 45.0F));
		if (debug) {
			matrixStackIn.translate(f3, f4, f5);
		}
		float glowRadiusStart = glowRadius;
		for (int o = 0; o < beams; o++) {
			f6 = -glowRadius;
			float f7 = -glowRadius;
			f8 = -glowRadius;
			f9 = -glowRadius;
			f13 = 0.0F;
			f14 = 1.0F;
			f15 = -1.0F + f2;
			f16 = (float) height * textureScale + f15;
			if (!magic) {
				if (!texture) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RenderType.getBeaconBeam(textureLocation2, true)), f3,
							f4, f5, glowAmount, yOffset, i, f6 + offset, f7 + offset, glowRadius + offset, f8 + offset,
							f9 + offset, glowRadius + offset, glowRadius + offset, glowRadius + offset, 0.0F + offset,
							1.0F + offset, f16 + offset, f15 + offset);
				} else {
					renderPart(matrixStackIn, bufferIn.getBuffer(RenderType.getBeaconBeam(textureLocation, true)), f3,
							f4, f5, glowAmount, yOffset, i, f6 + offset, f7 + offset, glowRadius + offset, f8 + offset,
							f9 + offset, glowRadius + offset, glowRadius + offset, glowRadius + offset, 0.0F + offset,
							1.0F + offset, f16 + offset, f15 + offset);
				}
			} else {
				if (background) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0F, 0F, 0F, 1F, yOffset, i,
							f6 + offset, f7 + offset, glowRadius + offset, f8 + offset, f9 + offset, glowRadius + offset,
							glowRadius + offset, glowRadius + offset, 0.0F + offset, 1.0F + offset, f16 + offset,
							f15 + offset);
				}
				for (int l = 1; l < passes; l++) {
					renderPart(matrixStackIn, bufferIn.getBuffer(RENDER_TYPES.get(l)), f3, f4, f5, glowAmount/beamRadius, yOffset,
							i, f6 + offset, f7 + offset, glowRadius + offset, f8 + offset, f9 + offset,
							glowRadius + offset, glowRadius + offset, glowRadius + offset, 0.0F + offset, 1.0F + offset,
							f16 + offset, f15 + offset);
				}
			}
			glowRadius = glowRadius + glowRadiusStart;
		}
		matrixStackIn.pop();
		matrixStackIn.pop();
	}

	private static void renderPart(MatrixStack matrixStackIn, IVertexBuilder bufferIn, float red, float green,
			float blue, float alpha, int yMin, int yMax, float p_228840_8_, float p_228840_9_, float p_228840_10_,
			float p_228840_11_, float p_228840_12_, float p_228840_13_, float p_228840_14_, float p_228840_15_,
			float u1, float u2, float v1, float v2) {
		MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
		Matrix4f matrix4f = matrixstack$entry.getMatrix();
		Matrix3f matrix3f = matrixstack$entry.getNormal();
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_8_, p_228840_9_,
				p_228840_10_, p_228840_11_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_14_, p_228840_15_,
				p_228840_12_, p_228840_13_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_10_, p_228840_11_,
				p_228840_14_, p_228840_15_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_12_, p_228840_13_,
				p_228840_8_, p_228840_9_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_10_, p_228840_11_,
				p_228840_8_, p_228840_9_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_12_, p_228840_13_,
				p_228840_14_, p_228840_15_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_14_, p_228840_15_,
				p_228840_10_, p_228840_11_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_8_, p_228840_9_,
				p_228840_12_, p_228840_13_, u1, u2, v1, v2);
	}
	
	private void renderSelectionBlock(BeaconTileEntity tileEntityIn, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, BlockColors color, double x, double y, double z) {
		double d0 = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
		int k = this.getPasses(d0);
		float radius = 0.5625F;
		matrixStackIn.push();
		matrixStackIn.translate(x + 0.5, y + 0.5, z + 0.5);
		for (int l = 1; l < k; l++) {
			renderCube(matrixStackIn, bufferIn.getBuffer(RENDER_TYPES.get(l)), color.red / 256, color.green / 256,
					color.blue / 256, color.alpha, -radius, radius, -radius, -radius, radius, -radius, -radius, radius,
					radius, radius, 0F, 1F, 1F, 0F);
		}
		matrixStackIn.pop();
	}
	
	private static void renderNetherStar(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float radius,
			long totalWorldTime, float partialTicks) {
		renderNetherStar(matrixStackIn, bufferIn, 1F, 1F, 1F, 1F, radius, totalWorldTime, partialTicks);
	}

	private static void renderNetherStar(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float red, float green,
			float blue, float alpha, float radius, long totalWorldTime, float partialTicks) {
		float f = (float) Math.floorMod(totalWorldTime, 160L) + partialTicks;
		matrixStackIn.push();
		matrixStackIn.translate(0.5F, 0.125F, 0.5F);
		float f1 = (float) (totalWorldTime + partialTicks) * 3.0F;
		float f2 = sinWave(totalWorldTime, partialTicks);
		matrixStackIn.translate(0.0D, (double) (1.5F + f2 / 2.0F), 0.0D);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
		matrixStackIn.rotate(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
		renderCube(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySmoothCutout(TEXTURE_OUT)), red, green, blue,
				alpha, -radius, radius, -radius, -radius, radius, -radius, -radius, radius, radius, radius, 0F, 1F, 1F,
				0F);
		matrixStackIn.scale(0.875F, 0.875F, 0.875F);
		matrixStackIn.rotate(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
		renderCube(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySmoothCutout(TEXTURE_OUT)), red, green, blue,
				alpha, -radius, radius, -radius, -radius, radius, -radius, -radius, radius, radius, radius, 0F, 1F, 1F,
				0F);
		matrixStackIn.scale(0.875F, 0.875F, 0.875F);
		matrixStackIn.rotate(new Quaternion(new Vector3f(PiSin, 0.0F, PiSin), 60.0F, true));
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
		renderCube(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySmoothCutout(TEXTURE_IN)), red, green, blue,
				alpha, -radius, radius, -radius, -radius, radius, -radius, -radius, radius, radius, radius, 0F, 1F, 1F,
				0F);
		matrixStackIn.pop();
	}

	private static void renderCube(MatrixStack matrixStackIn, IVertexBuilder bufferIn, float red, float green,
			float blue, float alpha, float yMin, float yMax, float p_228840_8_, float p_228840_9_, float p_228840_10_,
			float p_228840_11_, float p_228840_12_, float p_228840_13_, float p_228840_14_, float p_228840_15_,
			float u1, float u2, float v1, float v2) {
		MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
		Matrix4f matrix4f = matrixstack$entry.getMatrix();
		Matrix3f matrix3f = matrixstack$entry.getNormal();
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_10_, p_228840_11_,
				p_228840_8_, p_228840_9_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_12_, p_228840_13_,
				p_228840_14_, p_228840_15_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_14_, p_228840_15_,
				p_228840_10_, p_228840_11_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_8_, p_228840_9_,
				p_228840_12_, p_228840_13_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_8_, p_228840_9_,
				p_228840_10_, p_228840_11_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_14_, p_228840_15_,
				p_228840_12_, p_228840_13_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_10_, p_228840_11_,
				p_228840_14_, p_228840_15_, u1, u2, v1, v2);
		addQuad(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, yMax, p_228840_12_, p_228840_13_,
				p_228840_8_, p_228840_9_, u1, u2, v1, v2);
		addQuadFlat(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMax, p_228840_9_, p_228840_11_,
				p_228840_13_, p_228840_15_, u1, u2, v1, v2);
		addQuadFlat(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMax, p_228840_9_, p_228840_15_,
				p_228840_13_, p_228840_11_, u1, u2, v1, v2);
		addQuadFlat(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, p_228840_9_, p_228840_11_,
				p_228840_13_, p_228840_15_, u1, u2, v1, v2);
		addQuadFlat(matrix4f, matrix3f, bufferIn, red, green, blue, alpha, yMin, p_228840_9_, p_228840_15_,
				p_228840_13_, p_228840_11_, u1, u2, v1, v2);
	}

	private static void addQuad(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder bufferIn, float red,
			float green, float blue, float alpha, float yMin, float yMax, float x1, float z1, float x2, float z2,
			float u1, float u2, float v1, float v2) {
		addVertex(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, yMax, x1, z1, u2, v1);
		addVertex(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, yMin, x1, z1, u2, v2);
		addVertex(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, yMin, x2, z2, u1, v2);
		addVertex(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, yMax, x2, z2, u1, v1);
	}

	private static void addQuadFlat(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder bufferIn, float red,
			float green, float blue, float alpha, float y, float x1, float z1, float x2, float z2, float u1, float u2,
			float v1, float v2) {
		addVertexFy(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, y, x2, z1, u2, v1);
		addVertexFy(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, y, x2, z2, u2, v2);
		addVertexFy(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, y, x1, z2, u1, v2);
		addVertexFy(matrixPos, matrixNormal, bufferIn, red, green, blue, alpha, y, x1, z1, u1, v1);
	}

	private static void addVertex(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder bufferIn, float red,
			float green, float blue, float alpha, float y, float x, float z, float texU, float texV) {
		bufferIn.pos(matrixPos, x, y, z).color(red, green, blue, alpha).tex(texU, texV)
				.overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrixNormal, 0.0F, 1.0F, 0.0F)
				.endVertex();
	}

	private static void addVertexFy(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder bufferIn, float red,
			float green, float blue, float alpha, float y, float x, float z, float texU, float texV) {
		bufferIn.pos(matrixPos, x, y, z).color(red, green, blue, alpha).tex(texU, texV)
				.overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrixNormal, 0.0F, 1.0F, 0.0F)
				.endVertex();
	}
	
	public static float sinWave(long totalWorldTime, float partialTicks) {
		float f = (float) totalWorldTime + partialTicks;
		float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 2.4F;
	}
	protected int getPasses(double p_191286_1_) {
		if (p_191286_1_ > 36864.0D) {
			return 1;
		} else if (p_191286_1_ > 25600.0D) {
			return 3;
		} else if (p_191286_1_ > 16384.0D) {
			return 5;
		} else if (p_191286_1_ > 9216.0D) {
			return 7;
		} else if (p_191286_1_ > 4096.0D) {
			return 9;
		} else if (p_191286_1_ > 1024.0D) {
			return 11;
		} else if (p_191286_1_ > 576.0D) {
			return 13;
		} else {
			return p_191286_1_ > 256.0D ? 14 : 15;
		}
	}
}