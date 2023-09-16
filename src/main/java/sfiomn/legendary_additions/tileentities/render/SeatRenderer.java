package sfiomn.legendary_additions.tileentities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sfiomn.legendary_additions.entities.SeatEntity;

public class SeatRenderer extends EntityRenderer<SeatEntity> {
    public SeatRenderer(EntityRendererManager manager)
    {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity seatEntity)
    {
        return null;
    }

    @Override
    protected boolean shouldShowName(SeatEntity p_177070_1_) {
        return false;
    }

    @Override
    public boolean shouldRender(SeatEntity p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return false;
    }
}
