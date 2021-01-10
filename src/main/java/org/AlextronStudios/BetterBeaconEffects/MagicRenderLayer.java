package org.AlextronStudios.BetterBeaconEffects;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.List;
import java.util.stream.IntStream;

public class MagicRenderLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private final ModelRenderer box;
    private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
        return RenderType.getEndPortal(p_228882_0_ + 1);
    }).collect(ImmutableList.toImmutableList());

    public MagicRenderLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
        this.box = new ModelRenderer(this.getEntityModel(), 0, 0);
        this.box.setTextureSize(1,1);
        this.box.addBox(-0.5F, 0F, -0.5F, 1F, 2F, 1F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.getUniqueID().toString().equals("e5878e7a-6d32-44f4-ada7-7bc8593e4db2") || entitylivingbaseIn.getUniqueID().toString().equals("398c7569-3f15-4998-b0d9-be91a9ac935c")  || entitylivingbaseIn.getName().equals("Dev")) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RENDER_TYPES.get(1));
            this.box.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 127F, 0F, 255F, 1F);
        }
    }
}
