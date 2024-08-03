package sfiomn.legendary_additions.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LegendaryAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ItemRegistry.TINY_XP_BOTTLE_ITEM.get());
        basicItem(ItemRegistry.COMMON_XP_BOTTLE_ITEM.get());
        basicItem(ItemRegistry.RARE_XP_BOTTLE_ITEM.get());
        basicItem(ItemRegistry.EPIC_XP_BOTTLE_ITEM.get());
        basicItem(ItemRegistry.LEGENDARY_XP_BOTTLE_ITEM.get());

        basicItem(this.modLoc("moss"));

        singleTexture("acacia_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/acacia_window_pane"));
        singleTexture("birch_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/birch_window"));
        singleTexture("crimson_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/crimson_window_pane"));
        singleTexture("dark_oak_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/dark_oak_window_pane"));
        singleTexture("jungle_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/jungle_window"));
        singleTexture("oak_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/oak_window"));
        singleTexture("ornate_iron_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/ornate_iron_window_pane"));
        singleTexture("spruce_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/spruce_window_pane"));
        singleTexture("warped_window_pane", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/warped_window_pane"));

        singleTexture("glowing_bulb", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("block/glowing_bulb_top"));

        singleTexture("obelisk", new ResourceLocation("item/generated"),
                "layer0", this.modLoc("item/obelisk_item"));
    }
}
