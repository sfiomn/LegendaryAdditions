package sfiomn.legendary_additions.tileentities.model;

import net.minecraft.util.ResourceLocation;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.ForestDungeonGateTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ForestDungeonGateModel extends AnimatedGeoModel<ForestDungeonGateTileEntity> {
    @Override
    public ResourceLocation getModelLocation(ForestDungeonGateTileEntity object) {
        if (object.isOpened())
            return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/forest_dungeon_gate_opened.geo.json");
        else
            return new ResourceLocation(LegendaryAdditions.MOD_ID, "geo/forest_dungeon_gate.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ForestDungeonGateTileEntity object) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/entity/forest_dungeon_gate.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ForestDungeonGateTileEntity animatable) {
        return new ResourceLocation(LegendaryAdditions.MOD_ID, "animations/forest_dungeon_gate.animation.json");
    }
}
