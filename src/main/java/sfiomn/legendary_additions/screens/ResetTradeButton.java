package sfiomn.legendary_additions.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.network.packets.ResetTradeMessage;

public class ResetTradeButton extends Button {
    private final ResourceLocation RESET_TRADE_BUTTON = new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/gui/reset_trade_button.png");
    public static final int BUTTON_HEIGHT = 17;
    public static final int BUTTON_WIDTH = 17;
    private int leftScreenPos;
    private int topScreenPos;
    public Player player;
    public MerchantMenu merchantMenu;
    public Item costItem;
    public boolean isEnabled;

    public ResetTradeButton(Player player, MerchantMenu merchantMenu, int leftScreenPos, int topScreenPos) {
        super(leftScreenPos + 107 + Config.Baked.resetTradeButtonOffsetX, topScreenPos + 52 + Config.Baked.resetTradeButtonOffsetY, BUTTON_WIDTH, BUTTON_HEIGHT, Component.literal(""), button -> ResetTradeMessage.sendToServer(), DEFAULT_NARRATION);
        this.player = player;
        this.merchantMenu = merchantMenu;
        this.leftScreenPos = leftScreenPos;
        this.topScreenPos = topScreenPos;
        this.setTooltip(Tooltip.create(Component.translatable("tooltip." + LegendaryAdditions.MOD_ID + ".reset_trade_button.description_no_cost")));
        int costAmount = Config.Baked.resetTradeCostAmount;
        String costItemName = Config.Baked.resetTradeCostItem;
        if (costAmount > 0 & ResourceLocation.isValidResourceLocation(costItemName)) {
            costItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(costItemName));
            if (costItem != null) {
                this.setTooltip(Tooltip.create(Component.translatable("tooltip." + LegendaryAdditions.MOD_ID + ".reset_trade_button.description_with_cost", costAmount, costItem.getDescription())));
            }
        }
    }

    public void checkEnabled() {
        this.isEnabled = canPlayerPayCost();
    }

    public boolean canPlayerPayCost() {
        if (costItem != null)
            return player.getInventory().countItem(costItem) >= Config.Baked.resetTradeCostAmount;
        return true;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partial) {
        int disabledOffsetX = 0;
        int mouseOverOffsetY = 0;
        if (!this.isEnabled)
            disabledOffsetX = 17;
        else if (this.isMouseOver(mouseX, mouseY)) {
            mouseOverOffsetY = 17;
        }

        gui.blit(RESET_TRADE_BUTTON, this.getX(), this.getY(), disabledOffsetX, mouseOverOffsetY, BUTTON_WIDTH, BUTTON_HEIGHT, 34, 34);
    }

    public void updatePosition(int leftScreenPos, int topScreenPos) {
        if (this.leftScreenPos != leftScreenPos || this.topScreenPos != topScreenPos) {
            this.leftScreenPos = leftScreenPos;
            this.topScreenPos = topScreenPos;
            setX(leftScreenPos + 107 + Config.Baked.resetTradeButtonOffsetX);
            setY(topScreenPos + 54 + Config.Baked.resetTradeButtonOffsetY);
        }
    }

    public boolean hasVillagerXp() {
        return this.merchantMenu.getTraderXp() > 0;
    }
}
