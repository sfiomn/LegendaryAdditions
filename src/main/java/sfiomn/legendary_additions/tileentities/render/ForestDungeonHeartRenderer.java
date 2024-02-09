package sfiomn.legendary_additions.tileentities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.ForestDungeonHeartTileEntity;
import sfiomn.legendary_additions.tileentities.ObeliskTileEntity;
import sfiomn.legendary_additions.tileentities.model.ForestDungeonHeartModel;
import sfiomn.legendary_additions.tileentities.model.ObeliskModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class ForestDungeonHeartRenderer extends GeoBlockRenderer<ForestDungeonHeartTileEntity> {
    public ForestDungeonHeartRenderer(TileEntityRendererDispatcher renderManager) {
        super(renderManager, new ForestDungeonHeartModel());
    }

    @Override
    public RenderType getRenderType(ForestDungeonHeartTileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
