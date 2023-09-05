package sfiomn.legendary_additions.entities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.ObeliskTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ObeliskBlockModel extends AnimatedGeoModel<ObeliskTileEntity> {
    @Override
    public ResourceLocation getModelLocation(ObeliskTileEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/obelisk.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ObeliskTileEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/entity/obelisk.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ObeliskTileEntity animatable) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "animations/obelisk.animation.json");
    }
}
