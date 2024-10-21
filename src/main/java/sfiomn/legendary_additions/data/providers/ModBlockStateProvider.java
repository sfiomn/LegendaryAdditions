package sfiomn.legendary_additions.data.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.HoneyPondBlock;
import sfiomn.legendary_additions.blocks.MeatRackBlock;
import sfiomn.legendary_additions.blocks.VerticalBlock;
import sfiomn.legendary_additions.blocks.XpStorageBlock;
import sfiomn.legendary_additions.registry.BlockRegistry;


public class ModBlockStateProvider extends BlockStateProvider {

    public static final ResourceLocation GLOWING_BULB_BOTTOM = new ResourceLocation(LegendaryAdditions.MOD_ID, "block/glowing_bulb_bottom");
    public static final ResourceLocation GLOWING_BULB_TOP = new ResourceLocation(LegendaryAdditions.MOD_ID, "block/glowing_bulb_top");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LegendaryAdditions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        this.getVariantBuilder(BlockRegistry.ANCESTRAL_WOOD.get())
                        .partialState()
                                .with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                                .modelForState()
                                    .modelFile(this.models().cubeAll("ancestral_wood_1", this.modLoc("block/ancestral_wood_1")))
                                    .weight(40).rotationX(90).rotationY(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_2", this.modLoc("block/ancestral_wood_2")))
                                    .weight(40).rotationX(90).rotationY(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_3", this.modLoc("block/ancestral_wood_3")))
                                    .weight(10).rotationX(90).rotationY(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_4", this.modLoc("block/ancestral_wood_4")))
                                    .weight(10).rotationX(90).rotationY(90)
                                .addModel()
                            .partialState()
                                .with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                                .modelForState()
                                    .modelFile(this.models().cubeAll("ancestral_wood_1", this.modLoc("block/ancestral_wood_1")))
                                    .weight(40)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_2", this.modLoc("block/ancestral_wood_2")))
                                    .weight(40)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_3", this.modLoc("block/ancestral_wood_3")))
                                    .weight(10)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_4", this.modLoc("block/ancestral_wood_4")))
                                    .weight(10)
                                .addModel()
                            .partialState()
                                .with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                                .modelForState()
                                    .modelFile(this.models().cubeAll("ancestral_wood_1", this.modLoc("block/ancestral_wood_1")))
                                    .weight(40).rotationX(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_2", this.modLoc("block/ancestral_wood_2")))
                                    .weight(40).rotationX(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_3", this.modLoc("block/ancestral_wood_3")))
                                    .weight(10).rotationX(90)
                                .nextModel()
                                    .modelFile(this.models().cubeAll("ancestral_wood_4", this.modLoc("block/ancestral_wood_4")))
                                    .weight(10).rotationX(90)
                                .addModel();

        this.simpleBlockItem(BlockRegistry.ANCESTRAL_WOOD.get(), this.models().cubeAll("ancestral_wood_1", this.modLoc("block/ancestral_wood_1")));

        simpleBlockWithItem(BlockRegistry.CARVED_ACACIA_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_ACACIA_LOG_BLOCK.get()));
        simpleBlockWithItem(BlockRegistry.CARVED_BIRCH_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_BIRCH_LOG_BLOCK.get()));
        simpleBlockWithItem(BlockRegistry.CARVED_DARK_OAK_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_DARK_OAK_LOG_BLOCK.get()));
        simpleBlockWithItem(BlockRegistry.CARVED_JUNGLE_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_JUNGLE_LOG_BLOCK.get()));
        simpleBlockWithItem(BlockRegistry.CARVED_OAK_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_OAK_LOG_BLOCK.get()));
        simpleBlockWithItem(BlockRegistry.CARVED_SPRUCE_LOG_BLOCK.get(), cubeAll(BlockRegistry.CARVED_SPRUCE_LOG_BLOCK.get()));

        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_BLACK.get(), "quartz_lamp_black");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_BLUE.get(), "quartz_lamp_blue");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_BROWN.get(), "quartz_lamp_brown");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_CYAN.get(), "quartz_lamp_cyan");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_GRAY.get(), "quartz_lamp_gray");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_GREEN.get(), "quartz_lamp_green");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_LIGHT_BLUE.get(), "quartz_lamp_light_blue");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_LIGHT_GRAY.get(), "quartz_lamp_light_gray");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_LIME.get(), "quartz_lamp_lime");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_MAGENTA.get(), "quartz_lamp_magenta");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_ORANGE.get(), "quartz_lamp_orange");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_PINK.get(), "quartz_lamp_pink");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_PURPLE.get(), "quartz_lamp_purple");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_RED.get(), "quartz_lamp_red");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_WHITE.get(), "quartz_lamp_white");
        createQuartzLamp(BlockRegistry.QUARTZ_LAMP_YELLOW.get(), "quartz_lamp_yellow");

        horizontalBlock(BlockRegistry.CAPTAIN_CHAIR_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/captain_chair")), 90);
        horizontalBlock(BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/captain_chair_top")), 90);

        this.getVariantBuilder(BlockRegistry.GLOWING_BULB_BLOCK.get())
                .partialState()
                    .with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState()
                    .modelFile(models().cross("glowing_bulb_bottom", GLOWING_BULB_BOTTOM).texture("particle", GLOWING_BULB_BOTTOM).renderType("cutout"))
                    .addModel()
                .partialState()
                    .with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState()
                    .modelFile(models().cross("glowing_bulb", GLOWING_BULB_TOP).texture("particle", GLOWING_BULB_TOP).renderType("cutout"))
                    .addModel();

        cubeWithItem(BlockRegistry.HIVE_LANTERN_BLOCK.get(), "hive_lantern", this.modLoc("block/hive_lantern_top"), this.modLoc("block/hive_lantern"));

        this.getVariantBuilder(BlockRegistry.HONEY_POND_BLOCK.get())
                .partialState()
                    .with(HoneyPondBlock.HONEY_POND_STATE, 2)
                    .modelForState()
                    .modelFile(new ModelFile.UncheckedModelFile(this.modLoc("block/honey_pond")))
                    .addModel()
                .partialState()
                    .with(HoneyPondBlock.HONEY_POND_STATE, 0)
                    .modelForState()
                    .modelFile(new ModelFile.UncheckedModelFile(this.modLoc("block/honey_pond_empty")))
                    .addModel()
                .partialState()
                    .with(HoneyPondBlock.HONEY_POND_STATE, 1)
                    .modelForState()
                    .modelFile(new ModelFile.UncheckedModelFile(this.modLoc("block/honey_pond_midcap")))
                    .addModel();

        VariantBlockStateBuilder meatRackBuilder = this.getVariantBuilder(BlockRegistry.MEAT_RACK_BLOCK.get());
        for (int i=0; i<4; i++) {
            ResourceLocation blockModel;
            if (i == 0)
                blockModel = this.modLoc("block/meat_rack_empty");
            else if (i == 1)
                blockModel = this.modLoc("block/meat_rack");
            else if (i == 2)
                blockModel = this.modLoc("block/meat_rack_leather");
            else
                blockModel = this.modLoc("block/meat_rack_bone");

            int finalI = i;
            PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
                        if (dir.getAxis().isHorizontal()) {
                            meatRackBuilder
                                    .partialState()
                                    .with(MeatRackBlock.MEAT_RACK_STATE, finalI)
                                    .with(MeatRackBlock.FACING, dir)
                                    .modelForState()
                                    .modelFile(new ModelFile.UncheckedModelFile(blockModel))
                                    .rotationY(((int )(dir.toYRot() + 90)) % 360)
                                    .addModel();
                        }
                    });
        }
        this.simpleBlockItem(BlockRegistry.MEAT_RACK_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/meat_rack_empty")));

        this.simpleBlock(BlockRegistry.MOSS_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/moss")));
        this.simpleBlockWithItem(BlockRegistry.OBELISK_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/obelisk")));

        VariantBlockStateBuilder xpStorageBuilder = this.getVariantBuilder(BlockRegistry.XP_STORAGE_BLOCK.get());
        for (int i=0; i<4; i++) {
            BlockModelBuilder blockModel = this.models().cube("xp_storage_" + i,
                            this.mcLoc("block/mossy_stone_bricks"),
                            this.mcLoc("block/mossy_stone_bricks"),
                            this.modLoc("block/xp_storage_" + i),
                            this.modLoc("block/xp_storage_" + i),
                            this.modLoc("block/xp_storage_" + i),
                            this.modLoc("block/xp_storage_" + i))
                    .texture("particle", this.mcLoc("block/mossy_stone_bricks"));
            xpStorageBuilder
                    .partialState()
                    .with(XpStorageBlock.STATE, i)
                    .modelForState()
                    .modelFile(blockModel)
                    .addModel();
            if (i == 0)
                this.simpleBlockItem(BlockRegistry.XP_STORAGE_BLOCK.get(), blockModel);
        }

        this.simpleBlock(BlockRegistry.SPIDER_EGGS_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/spider_eggs")));
        this.simpleBlock(BlockRegistry.TRIBAL_TORCH_DOWN_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/tribal_torch_down")));
        this.simpleBlock(BlockRegistry.TRIBAL_TORCH_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/tribal_torch")));
        this.horizontalBlock(BlockRegistry.TRIBAL_TORCH_WALL_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/tribal_torch_wall")));
        this.simpleBlockItem(BlockRegistry.TRIBAL_TORCH_BLOCK.get(), new ModelFile.UncheckedModelFile(this.modLoc("block/tribal_torch")));

        // Vertical Window Blocks
        verticalWindowBlock(BlockRegistry.ACACIA_WINDOW_BLOCK.get(), "acacia_window", this.mcLoc("block/acacia_planks"));
        verticalPaneBlock(BlockRegistry.ACACIA_WINDOW_PANE.get(), "acacia_window");

        verticalWindowBlock(BlockRegistry.CRIMSON_WINDOW_BLOCK.get(), "crimson_window", this.mcLoc("block/crimson_planks"));
        verticalPaneBlock(BlockRegistry.CRIMSON_WINDOW_PANE.get(), "crimson_window");

        verticalWindowBlock(BlockRegistry.DARK_OAK_WINDOW_BLOCK.get(), "dark_oak_window", this.mcLoc("block/dark_oak_planks"));
        verticalPaneBlock(BlockRegistry.DARK_OAK_WINDOW_PANE.get(), "dark_oak_window");

        verticalWindowBlock(BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get(), "ornate_iron_window", this.modLoc("block/ornate_iron_window_top"));
        verticalPaneBlock(BlockRegistry.ORNATE_IRON_WINDOW_PANE.get(), "ornate_iron_window");

        verticalWindowBlock(BlockRegistry.SPRUCE_WINDOW_BLOCK.get(), "spruce_window", this.mcLoc("block/spruce_planks"));
        verticalPaneBlock(BlockRegistry.SPRUCE_WINDOW_PANE.get(), "spruce_window");

        verticalWindowBlock(BlockRegistry.WARPED_WINDOW_BLOCK.get(), "warped_window", this.mcLoc("block/warped_planks"));
        verticalPaneBlock(BlockRegistry.WARPED_WINDOW_PANE.get(), "warped_window");

        // Window Blocks
        basicWindowBlockWithItem(BlockRegistry.BIRCH_WINDOW_BLOCK.get(), "birch_window", this.mcLoc("block/birch_planks"), this.modLoc("block/birch_window"));
        basicPaneBlockWithModel(BlockRegistry.BIRCH_WINDOW_PANE.get(), "birch_window");

        basicWindowBlockWithItem(BlockRegistry.JUNGLE_WINDOW_BLOCK.get(), "jungle_window", this.mcLoc("block/jungle_planks"), this.modLoc("block/jungle_window"));
        basicPaneBlockWithModel(BlockRegistry.JUNGLE_WINDOW_PANE.get(), "jungle_window");

        basicWindowBlockWithItem(BlockRegistry.OAK_WINDOW_BLOCK.get(), "oak_window", this.mcLoc("block/oak_planks"), this.modLoc("block/oak_window"));
        basicPaneBlockWithModel(BlockRegistry.OAK_WINDOW_PANE.get(), "oak_window");
    }

    public void createQuartzLamp(Block quartzLampBlock, String name) {
        ResourceLocation modUnlit = this.modLoc("block/" + name);
        ResourceLocation modelLit = this.modLoc("block/" + name + "_on");
        ModelFile unlitQuartzBlock = this.models().cubeAll("block/quartz_lamp/" + name, modUnlit).texture("particle", modUnlit);
        this.getVariantBuilder(quartzLampBlock)
                .partialState()
                .with(BlockStateProperties.LIT, Boolean.TRUE)
                .modelForState()
                .modelFile(models().cubeAll("block/quartz_lamp/" + name + "_on", modelLit).texture("particle", modelLit))
                .addModel()
                .partialState()
                .with(BlockStateProperties.LIT, Boolean.FALSE)
                .modelForState()
                .modelFile(unlitQuartzBlock)
                .addModel();

        simpleBlockItem(quartzLampBlock, unlitQuartzBlock);
    }

    public void cubeWithItem(Block block, String baseName, ResourceLocation topTexture, ResourceLocation sideTexture) {
        ModelFile blockModel = this.models().cube(baseName, topTexture, topTexture, sideTexture, sideTexture, sideTexture, sideTexture).texture("particle", topTexture).renderType("cutout_mipped");
        simpleBlock(block, blockModel);
        simpleBlockItem(block, blockModel);
    }

    public void basicWindowBlockWithItem(Block block, String baseName, ResourceLocation topTexture, ResourceLocation sideTexture) {
        ModelFile blockModel = this.models().cube("block/" + baseName + "/block", topTexture, topTexture, sideTexture, sideTexture, sideTexture, sideTexture).texture("particle", topTexture).renderType("cutout_mipped");
        simpleBlock(block, blockModel);
        simpleBlockItem(block, blockModel);
    }

    public void basicPaneBlockWithModel(Block block, String baseName) {
        ResourceLocation paneLoc = this.modLoc("block/" + baseName);
        ResourceLocation edgeLoc = this.modLoc("block/" + baseName + "_pane_edge");

        ModelFile post = this.models().panePost("block/" + baseName + "/pane_post", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile side = this.models().paneSide("block/" + baseName + "/pane_side", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideAlt = this.models().paneSideAlt("block/" + baseName + "/pane_side_alt", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile noSide = this.models().paneNoSide("block/" + baseName + "/pane_noside", paneLoc).renderType("cutout_mipped");
        ModelFile noSideAlt = this.models().paneNoSideAlt("block/" + baseName + "/pane_noside_alt", paneLoc).renderType("cutout_mipped");
        this.paneBlock((IronBarsBlock) block, post, side, sideAlt, noSide, noSideAlt);
    }

    private void verticalWindowBlock(Block block, String baseName, ResourceLocation topTexture) {
        ResourceLocation paneLoc = this.modLoc("block/" + baseName + "_pane");
        ResourceLocation paneBottomLoc = this.modLoc("block/" + baseName + "_pane_bottom");
        ResourceLocation paneMiddleLoc = this.modLoc("block/" + baseName + "_pane_middle");
        ResourceLocation paneTopLoc = this.modLoc("block/" + baseName + "_pane_top");

        ModelFile blockModel = this.models().cube("block/" + baseName + "/block", topTexture, topTexture, paneLoc, paneLoc, paneLoc, paneLoc).texture("particle", topTexture).renderType("cutout_mipped");
        ModelFile blockBottomModel = this.models().cube("block/" + baseName + "/block_bottom", topTexture, topTexture, paneBottomLoc, paneBottomLoc, paneBottomLoc, paneBottomLoc).texture("particle", topTexture).renderType("cutout_mipped");
        ModelFile blockMiddleModel = this.models().cube("block/" + baseName + "/block_middle", topTexture, topTexture, paneMiddleLoc, paneMiddleLoc, paneMiddleLoc, paneMiddleLoc).texture("particle", topTexture).renderType("cutout_mipped");
        ModelFile blockTopModel = this.models().cube("block/" + baseName + "/block_top", topTexture, topTexture, paneTopLoc, paneTopLoc, paneTopLoc, paneTopLoc).texture("particle", topTexture).renderType("cutout_mipped");

        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        this.verticalVariantBlock(builder, blockModel, Boolean.FALSE, Boolean.FALSE);
        this.verticalVariantBlock(builder, blockBottomModel, Boolean.TRUE, Boolean.FALSE);
        this.verticalVariantBlock(builder, blockMiddleModel, Boolean.TRUE, Boolean.TRUE);
        this.verticalVariantBlock(builder, blockTopModel, Boolean.FALSE, Boolean.TRUE);
        this.simpleBlockItem(block, blockModel);
    }

    private void verticalPaneBlock(Block block, String baseName) {
        ResourceLocation paneLoc = this.modLoc("block/" + baseName + "_pane");
        ResourceLocation edgeLoc = this.modLoc("block/" + baseName + "_pane_edge");
        ResourceLocation paneBottomLoc = this.modLoc("block/" + baseName + "_pane_bottom");
        ResourceLocation paneMiddleLoc = this.modLoc("block/" + baseName + "_pane_middle");
        ResourceLocation paneTopLoc = this.modLoc("block/" + baseName + "_pane_top");

        ModelFile post = this.models().panePost("block/" + baseName + "/pane_post", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile postBottom = panePostBottom("block/" + baseName + "/pane_post_bottom", paneBottomLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile postMiddle = panePostMiddle("block/" + baseName + "/pane_post_middle", paneMiddleLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile postTop = panePostTop("block/" + baseName + "/pane_post_top", paneTopLoc, edgeLoc).renderType("cutout_mipped");

        ModelFile side = this.models().paneSide("block/" + baseName + "/pane_side", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideBottom = paneSideBottom("block/" + baseName + "/pane_side_bottom", paneBottomLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideMiddle = paneSideMiddle("block/" + baseName + "/pane_side_middle", paneMiddleLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideTop = paneSideTop("block/" + baseName + "/pane_side_top", paneTopLoc, edgeLoc).renderType("cutout_mipped");

        ModelFile sideAlt = this.models().paneSideAlt("block/" + baseName + "/pane_side_alt", paneLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideAltBottom = paneSideAltBottom("block/" + baseName + "/pane_side_alt_bottom", paneBottomLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideAltMiddle = paneSideAltMiddle("block/" + baseName + "/pane_side_alt_middle", paneMiddleLoc, edgeLoc).renderType("cutout_mipped");
        ModelFile sideAltTop = paneSideAltTop("block/" + baseName + "/pane_side_alt_top", paneTopLoc, edgeLoc).renderType("cutout_mipped");

        ModelFile noSide = this.models().paneNoSide("block/" + baseName + "/pane_noside", paneLoc).renderType("cutout_mipped");
        ModelFile noSideBottom = this.models().paneNoSide("block/" + baseName + "/pane_noside_bottom", paneBottomLoc).renderType("cutout_mipped");
        ModelFile noSideMiddle = this.models().paneNoSide("block/" + baseName + "/pane_noside_middle", paneMiddleLoc).renderType("cutout_mipped");
        ModelFile noSideTop = this.models().paneNoSide("block/" + baseName + "/pane_noside_top", paneTopLoc).renderType("cutout_mipped");

        ModelFile noSideAlt = this.models().paneNoSideAlt("block/" + baseName + "/pane_noside_alt", paneLoc).renderType("cutout_mipped");
        ModelFile noSideAltBottom = this.models().paneNoSideAlt("block/" + baseName + "/pane_noside_alt_bottom", paneBottomLoc).renderType("cutout_mipped");
        ModelFile noSideAltMiddle = this.models().paneNoSideAlt("block/" + baseName + "/pane_noside_alt_middle", paneMiddleLoc).renderType("cutout_mipped");
        ModelFile noSideAltTop = this.models().paneNoSideAlt("block/" + baseName + "/pane_noside_alt_top", paneTopLoc).renderType("cutout_mipped");

        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        this.fourWayVerticalMultipart(builder, post, side, sideAlt, noSide, noSideAlt, Boolean.FALSE, Boolean.FALSE);
        this.fourWayVerticalMultipart(builder, postBottom, sideBottom, sideAltBottom, noSideBottom, noSideAltBottom, Boolean.TRUE, Boolean.FALSE);
        this.fourWayVerticalMultipart(builder, postMiddle, sideMiddle, sideAltMiddle, noSideMiddle, noSideAltMiddle, Boolean.TRUE, Boolean.TRUE);
        this.fourWayVerticalMultipart(builder, postTop, sideTop, sideAltTop, noSideTop, noSideAltTop, Boolean.FALSE, Boolean.TRUE);
        this.simpleBlockItem(block, post);
    }

    public void verticalVariantBlock(VariantBlockStateBuilder builder, ModelFile blockModel, Boolean upCondition, Boolean downCondition) {

        builder
                .partialState()
                    .with(VerticalBlock.UP, upCondition)
                    .with(VerticalBlock.DOWN, downCondition)
                        .modelForState()
                        .modelFile(blockModel)
                        .addModel();
    }

    public void fourWayVerticalMultipart(MultiPartBlockStateBuilder builder, ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt, Boolean upCondition, Boolean downCondition) {
        builder
                .part()
                .modelFile(post)
                .addModel()
                .condition(VerticalBlock.UP, upCondition)
                .condition(VerticalBlock.DOWN, downCondition)
                .end();


        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                boolean alt = dir == Direction.SOUTH;
                builder
                        .part()
                            .modelFile(!alt && dir != Direction.WEST ? side : sideAlt)
                                .rotationY(dir.getAxis() == Direction.Axis.X ? 90 : 0)
                            .addModel()
                                .condition(value, true)
                                .condition(VerticalBlock.UP, upCondition)
                                .condition(VerticalBlock.DOWN, downCondition)
                        .end()
                        .part()
                            .modelFile(!alt && dir != Direction.EAST ? noSide : noSideAlt)
                                .rotationY(dir == Direction.WEST ? 270 : (dir == Direction.SOUTH ? 90 : 0))
                            .addModel()
                                .condition(value, false)
                                .condition(VerticalBlock.UP, upCondition)
                                .condition(VerticalBlock.DOWN, downCondition)
                        .end();
            }
        });
    }

    private BlockModelBuilder modPane(String name, String parent, ResourceLocation pane, ResourceLocation edge) {
        return this.models().withExistingParent(name, this.modLoc("block/" + parent)).texture("pane", pane).texture("edge", edge);
    }

    public BlockModelBuilder panePostBottom(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_post_bottom", pane, edge);
    }

    public BlockModelBuilder panePostMiddle(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_post_middle", pane, edge);
    }

    public BlockModelBuilder panePostTop(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_post_top", pane, edge);
    }

    public BlockModelBuilder paneSideBottom(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_bottom", pane, edge);
    }

    public BlockModelBuilder paneSideMiddle(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_middle", pane, edge);
    }

    public BlockModelBuilder paneSideTop(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_top", pane, edge);
    }

    public BlockModelBuilder paneSideAltBottom(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_alt_bottom", pane, edge);
    }

    public BlockModelBuilder paneSideAltMiddle(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_alt_middle", pane, edge);
    }

    public BlockModelBuilder paneSideAltTop(String name, ResourceLocation pane, ResourceLocation edge) {
        return this.modPane(name, "vertical_pane/template_glass_pane_side_alt_top", pane, edge);
    }
}
