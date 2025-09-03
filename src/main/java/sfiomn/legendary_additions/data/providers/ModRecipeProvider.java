package sfiomn.legendary_additions.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_ACACIA_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_ACACIA_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_ACACIA_WOOD), has(Items.STRIPPED_ACACIA_WOOD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_BIRCH_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_BIRCH_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_BIRCH_WOOD), has(Items.STRIPPED_BIRCH_WOOD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_DARK_OAK_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_DARK_OAK_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_DARK_OAK_WOOD), has(Items.STRIPPED_DARK_OAK_WOOD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_JUNGLE_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_JUNGLE_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_JUNGLE_WOOD), has(Items.STRIPPED_JUNGLE_WOOD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_OAK_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_OAK_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_OAK_WOOD), has(Items.STRIPPED_OAK_WOOD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CARVED_SPRUCE_LOG_BLOCK.get(), 8)
                .pattern("###")
                .pattern("#s#")
                .pattern("###")
                .define('#', Items.STRIPPED_SPRUCE_WOOD)
                .define('s', Items.STICK)
                .group("carved_wood")
                .unlockedBy(getHasName(Items.STRIPPED_SPRUCE_WOOD), has(Items.STRIPPED_SPRUCE_WOOD))
                .save(consumer);

        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_BLACK.get(), Tags.Items.DYES_BLACK, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_BLUE.get(), Tags.Items.DYES_BLUE, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_BROWN.get(), Tags.Items.DYES_BROWN, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_CYAN.get(), Tags.Items.DYES_CYAN, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_GRAY.get(), Tags.Items.DYES_GRAY, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_GREEN.get(), Tags.Items.DYES_GREEN, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_LIGHT_BLUE.get(), Tags.Items.DYES_LIGHT_BLUE, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_LIGHT_GRAY.get(), Tags.Items.DYES_LIGHT_GRAY, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_LIME.get(), Tags.Items.DYES_LIME, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_MAGENTA.get(), Tags.Items.DYES_MAGENTA, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_ORANGE.get(), Tags.Items.DYES_ORANGE, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_PINK.get(), Tags.Items.DYES_PINK, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_PURPLE.get(), Tags.Items.DYES_PURPLE, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_RED.get(), Tags.Items.DYES_RED, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_WHITE.get(), Tags.Items.DYES_WHITE, consumer);
        addQuartzLampRecipe(BlockRegistry.QUARTZ_LAMP_YELLOW.get(), Tags.Items.DYES_YELLOW, consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ACACIA_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.ACACIA_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.ACACIA_PLANKS), has(Items.ACACIA_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ACACIA_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.ACACIA_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.ACACIA_WINDOW_BLOCK.get()), has(BlockRegistry.ACACIA_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BIRCH_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.BIRCH_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.BIRCH_PLANKS), has(Items.BIRCH_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BIRCH_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.BIRCH_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.BIRCH_WINDOW_BLOCK.get()), has(BlockRegistry.BIRCH_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CRIMSON_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.CRIMSON_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.CRIMSON_PLANKS), has(Items.CRIMSON_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CRIMSON_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.CRIMSON_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.CRIMSON_WINDOW_BLOCK.get()), has(BlockRegistry.CRIMSON_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.DARK_OAK_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.DARK_OAK_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.DARK_OAK_PLANKS), has(Items.DARK_OAK_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.DARK_OAK_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.DARK_OAK_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.DARK_OAK_WINDOW_BLOCK.get()), has(BlockRegistry.DARK_OAK_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.JUNGLE_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.JUNGLE_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.JUNGLE_PLANKS), has(Items.JUNGLE_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.JUNGLE_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.JUNGLE_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.JUNGLE_WINDOW_BLOCK.get()), has(BlockRegistry.JUNGLE_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.OAK_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.OAK_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.OAK_PLANKS), has(Items.OAK_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.OAK_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.OAK_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.OAK_WINDOW_BLOCK.get()), has(BlockRegistry.OAK_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" i ")
                .pattern("igi")
                .define('i', Items.IRON_NUGGET)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ORNATE_IRON_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get()), has(BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.SPRUCE_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.SPRUCE_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.SPRUCE_PLANKS), has(Items.SPRUCE_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.SPRUCE_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.SPRUCE_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.SPRUCE_WINDOW_BLOCK.get()), has(BlockRegistry.SPRUCE_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WARPED_WINDOW_BLOCK.get())
                .pattern("   ")
                .pattern(" p ")
                .pattern("pgp")
                .define('p', Items.WARPED_PLANKS)
                .define('g', Tags.Items.GLASS)
                .unlockedBy(getHasName(Items.WARPED_PLANKS), has(Items.WARPED_PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WARPED_WINDOW_PANE.get(), 8)
                .pattern("   ")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', BlockRegistry.WARPED_WINDOW_BLOCK.get())
                .unlockedBy(getHasName(BlockRegistry.WARPED_WINDOW_BLOCK.get()), has(BlockRegistry.WARPED_WINDOW_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.HONEY_POND_BLOCK.get())
                .pattern("shs")
                .pattern(" b ")
                .pattern("ppp")
                .define('s', Items.STONE)
                .define('h', Items.HONEY_BOTTLE)
                .define('b', Items.BREWING_STAND)
                .define('p', ItemTags.PLANKS)
                .unlockedBy(getHasName(Items.BREWING_STAND), has(Items.BREWING_STAND))
                .unlockedBy(getHasName(Items.HONEY_BOTTLE), has(Items.HONEY_BOTTLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.MEAT_RACK_BLOCK.get())
                .pattern("ppp")
                .pattern("sts")
                .pattern("s s")
                .define('s', Items.STICK)
                .define('p', ItemTags.PLANKS)
                .define('t', Items.STRING)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TRIBAL_TORCH_BLOCK.get())
                .pattern("sts")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Items.STICK)
                .define('p', ItemTags.PLANKS)
                .define('t', Items.TORCH)
                .unlockedBy(getHasName(Items.TORCH), has(Items.TORCH))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DEATH_SCROLL_ITEM.get())
                .pattern("eee")
                .pattern("ppp")
                .pattern(" g ")
                .define('e', Items.ENDER_EYE)
                .define('p', Items.PAPER)
                .define('g', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.ENDER_EYE), has(Items.ENDER_EYE))
                .save(consumer);
    }

    private void addQuartzLampRecipe(Block quartzLamp, TagKey<Item> dyeItem, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, quartzLamp, 1)
                .pattern("#c#")
                .pattern("cgc")
                .pattern("#c#")
                .define('#', Items.QUARTZ)
                .define('g', Items.GLOWSTONE)
                .define('c', dyeItem)
                .group("quartz_lamp")
                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                .save(consumer);
    }
}
