package sfiomn.legendary_additions.tileentities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.ForestDungeonGateTileEntity;
import sfiomn.legendary_additions.tileentities.model.ForestDungeonGateModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class ForestDungeonGateRenderer extends GeoBlockRenderer<ForestDungeonGateTileEntity> {
    public ForestDungeonGateRenderer(TileEntityRendererDispatcher renderManager) {
        super(renderManager, new ForestDungeonGateModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ForestDungeonGateTileEntity instance) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/entity/forest_dungeon_gate.png");
    }

    @Override
    public RenderType getRenderType(ForestDungeonGateTileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
