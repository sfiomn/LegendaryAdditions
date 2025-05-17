package sfiomn.legendary_additions.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.mixin.MerchantMenuAccessor;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.util.VillagerTradeUtil;

import java.util.function.Supplier;

public class ResetTradeMessage
{
    // CLIENT to SERVER side message

    public ResetTradeMessage()
    {
    }

    public static void encode(ResetTradeMessage message, FriendlyByteBuf buffer)
    {
    }

    public static ResetTradeMessage decode(FriendlyByteBuf buffer)
    {
        return new ResetTradeMessage();
    }

    public static void handle(ResetTradeMessage message, Supplier<NetworkEvent.Context> supplier)
    {
        final NetworkEvent.Context context = supplier.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ServerPlayer player = context.getSender();
            if (player != null) {
                context.enqueueWork(() -> ResetTrade(player));
            }
        }
        supplier.get().setPacketHandled(true);
    }

    public static void ResetTrade(ServerPlayer player) {
        if (player.containerMenu instanceof MerchantMenu merchantMenu && player.containerMenu instanceof MerchantMenuAccessor merchantMenuAccessor) {
            Merchant merchant = merchantMenuAccessor.getTrader();
            if (merchant instanceof Villager villager) {
                if (!merchantMenu.getSlot(2).hasItem() && villager.getVillagerXp() == 0) {
                    boolean payed = true;
                    if (ResourceLocation.isValidResourceLocation(Config.Baked.resetTradeCostItem)) {
                        Item costItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Config.Baked.resetTradeCostItem));
                        if (Config.Baked.resetTradeCostAmount > 0 && costItem != null) {
                            payed = player.getInventory().countItem(costItem) >= Config.Baked.resetTradeCostAmount;
                            if (payed) {
                                player.getInventory().clearOrCountMatchingItems(itemStack -> itemStack.is(costItem), Config.Baked.resetTradeCostAmount, player.getInventory());
                            }
                        }
                    }
                    if (payed) {
                        MerchantOffers newMerchantOffers = new MerchantOffers();
                        villager.setOffers(newMerchantOffers);
                        VillagerTradeUtil.updateTrades(villager);
                        VillagerTradeUtil.updateSpecialPrices(villager, player);
                        player.sendMerchantOffers(merchantMenu.containerId, newMerchantOffers, villager.getVillagerData().getLevel(), villager.getVillagerXp(), villager.showProgressBar(), villager.canRestock());
                    }
                }
            }
        }
    }

    public static void sendToServer() {
        ResetTradeMessage messageResetTrade = new ResetTradeMessage();
        NetworkHandler.INSTANCE.sendToServer(messageResetTrade);
    }
}
