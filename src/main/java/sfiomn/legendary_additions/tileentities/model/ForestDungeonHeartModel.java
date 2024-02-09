package sfiomn.legendary_additions.tileentities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.ForestDungeonHeartTileEntity;
import sfiomn.legendary_additions.tileentities.ObeliskTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ForestDungeonHeartModel extends AnimatedGeoModel<ForestDungeonHeartTileEntity> {
    @Override
    public ResourceLocation getModelLocation(ForestDungeonHeartTileEntity animatable) {
        if (animatable.isActive())
            return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/forest_dungeon_heart.geo.json");
        else
            return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/forest_dungeon_heart_deactivated.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ForestDungeonHeartTileEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/entity/forest_dungeon_heart.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ForestDungeonHeartTileEntity animatable) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "animations/forest_dungeon_heart.animation.json");
    }
}
