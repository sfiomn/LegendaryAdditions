package sfiomn.legendary_additions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;

import java.util.ArrayList;
import java.util.List;

import static sfiomn.legendary_additions.items.HoneyPondItem.setHealingCapacityInTag;
import static sfiomn.legendary_additions.items.ObeliskItem.setXpInTag;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LEGENDARY_ADDITIONS_TAB = ITEM_GROUPS.register(LegendaryAdditions.MOD_ID, () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.LEGENDARY_XP_BOTTLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, list) ->
            {
                List<ItemStack> stacks = new ArrayList<>();
                CompoundTag tag;
                for (int xp: Config.Baked.obeliskXpValues) {
                    ItemStack stack = ItemRegistry.OBELISK_BLOCK_ITEM.get().getDefaultInstance();
                    tag = setXpInTag(stack.getTag(), xp);
                    stack.setTag(tag);
                    stacks.add(stack);
                }

                ItemStack honeyPondFull = new ItemStack(ItemRegistry.HONEY_POND_BLOCK_ITEM.get());
                tag = setHealingCapacityInTag(honeyPondFull.getTag(), Config.Baked.honeyPondMaxCapacity);
                honeyPondFull.setTag(tag);
                stacks.add(honeyPondFull);

                stacks.addAll(
                        List.of(
                                BlockRegistry.CARVED_ACACIA_LOG_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CARVED_BIRCH_LOG_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CARVED_DARK_OAK_LOG_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CARVED_JUNGLE_LOG_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CARVED_OAK_LOG_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CARVED_SPRUCE_LOG_BLOCK.get().asItem().getDefaultInstance(),

                                BlockRegistry.ANCESTRAL_WOOD.get().asItem().getDefaultInstance(),
                                BlockRegistry.HIVE_LANTERN_BLOCK.get().asItem().getDefaultInstance(),

                                BlockRegistry.QUARTZ_LAMP_BLACK.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_BLUE.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_BROWN.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_CYAN.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_GRAY.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_GREEN.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_LIGHT_BLUE.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_LIGHT_GRAY.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_LIME.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_MAGENTA.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_ORANGE.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_PINK.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_PURPLE.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_RED.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_WHITE.get().asItem().getDefaultInstance(),
                                BlockRegistry.QUARTZ_LAMP_YELLOW.get().asItem().getDefaultInstance(),

                                BlockRegistry.GLOWING_BULB_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.MOSS_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.MUD_TRAP_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.MEAT_RACK_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.HONEY_POND_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CAPTAIN_CHAIR_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.XP_STORAGE_BLOCK.get().asItem().getDefaultInstance(),

                                ItemRegistry.TINY_XP_BOTTLE_ITEM.get().getDefaultInstance(),
                                ItemRegistry.COMMON_XP_BOTTLE_ITEM.get().getDefaultInstance(),
                                ItemRegistry.RARE_XP_BOTTLE_ITEM.get().getDefaultInstance(),
                                ItemRegistry.EPIC_XP_BOTTLE_ITEM.get().getDefaultInstance(),
                                ItemRegistry.LEGENDARY_XP_BOTTLE_ITEM.get().getDefaultInstance(),

                                ItemRegistry.TRIBAL_TORCH.get().getDefaultInstance(),

                                BlockRegistry.ACACIA_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.ACACIA_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.BIRCH_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.BIRCH_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.CRIMSON_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.CRIMSON_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.DARK_OAK_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.DARK_OAK_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.JUNGLE_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.JUNGLE_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.OAK_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.OAK_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.ORNATE_IRON_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.SPRUCE_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.SPRUCE_WINDOW_PANE.get().asItem().getDefaultInstance(),
                                BlockRegistry.WARPED_WINDOW_BLOCK.get().asItem().getDefaultInstance(),
                                BlockRegistry.WARPED_WINDOW_PANE.get().asItem().getDefaultInstance()
                ));

                list.acceptAll(stacks);
            })
            .title(Component.translatable("itemGroup." + LegendaryAdditions.MOD_ID))
            .build());

    public static void register(IEventBus eventBus) {
        ITEM_GROUPS.register(eventBus);
    }
}
