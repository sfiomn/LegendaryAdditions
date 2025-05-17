package sfiomn.legendary_additions.util;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.Set;

public class VillagerTradeUtil {

    public VillagerTradeUtil(){}

    public static void updateTrades(Villager villager) {

        VillagerData villagerdata = villager.getVillagerData();
        Int2ObjectMap<VillagerTrades.ItemListing[]> int2objectmap = VillagerTrades.TRADES.get(villagerdata.getProfession());
        if (int2objectmap != null && !int2objectmap.isEmpty()) {
            VillagerTrades.ItemListing[] avillagertrades$itemlisting = int2objectmap.get(villagerdata.getLevel());
            if (avillagertrades$itemlisting != null) {
                MerchantOffers merchantoffers = villager.getOffers();
                Set<Integer> set = Sets.newHashSet();
                if (avillagertrades$itemlisting.length > 2) {
                    while(set.size() < 2) {
                        set.add(villager.getRandom().nextInt(avillagertrades$itemlisting.length));
                    }
                } else {
                    for(int i = 0; i < avillagertrades$itemlisting.length; ++i) {
                        set.add(i);
                    }
                }

                for (Integer integer : set) {
                    VillagerTrades.ItemListing villagertrades$itemlisting = avillagertrades$itemlisting[integer];
                    MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(villager, villager.getRandom());
                    if (merchantoffer != null) {
                        merchantoffers.add(merchantoffer);
                    }
                }
            }
        }
    }

    public static void updateSpecialPrices(Villager villager, Player player) {
        int i = villager.getPlayerReputation(player);
        if (i != 0) {

            for (MerchantOffer merchantoffer : villager.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float) i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
            MobEffectInstance mobeffectinstance = player.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
            if (mobeffectinstance != null) {
                int k = mobeffectinstance.getAmplifier();

                for (MerchantOffer merchantoffer : villager.getOffers()) {
                    double d0 = 0.3 + 0.0625 * (double) k;
                    int j = (int) Math.floor(d0 * (double) merchantoffer.getBaseCostA().getCount());
                    merchantoffer.addToSpecialPriceDiff(-Math.max(j, 1));
                }
            }
        }
    }
}
