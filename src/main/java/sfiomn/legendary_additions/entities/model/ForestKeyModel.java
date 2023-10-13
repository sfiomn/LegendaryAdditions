package sfiomn.legendary_additions.entities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.ForestKeyEntity;
import sfiomn.legendary_additions.tileentities.ObeliskTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ForestKeyModel extends AnimatedGeoModel<ForestKeyEntity> {
    @Override
    public ResourceLocation getModelLocation(ForestKeyEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/forest_key.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ForestKeyEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/entity/forest_dungeon_gate.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ForestKeyEntity animatable) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "animations/forest_key.animation.json");
    }
}
