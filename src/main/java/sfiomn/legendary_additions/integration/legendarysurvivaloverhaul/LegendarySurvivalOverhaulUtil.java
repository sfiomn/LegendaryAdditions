package sfiomn.legendary_additions.integration.legendarysurvivaloverhaul;

import net.minecraft.world.entity.player.Player;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendarysurvivaloverhaul.api.bodydamage.BodyDamageUtil;
import sfiomn.legendarysurvivaloverhaul.api.bodydamage.BodyPartEnum;

public class LegendarySurvivalOverhaulUtil {

    public static void healMostDamagedLimb(Player player, float healingValue) {
        if (LegendaryAdditions.legendarySurvivalOverhaulLoaded && player != null) {
            BodyPartEnum mostDamagedBodyPart = BodyPartEnum.CHEST;
            float minHealthRatio = 1.0f;
            for (BodyPartEnum bodyPartEnum: BodyPartEnum.values())
                if (BodyDamageUtil.getHealthRatio(player, bodyPartEnum) < minHealthRatio)
                    mostDamagedBodyPart = bodyPartEnum;
            BodyDamageUtil.healBodyPart(player, mostDamagedBodyPart, healingValue);
        }
    }

    public static boolean isALimbDamaged(Player player) {
        if (LegendaryAdditions.legendarySurvivalOverhaulLoaded && player != null) {
            for (BodyPartEnum bodyPartEnum: BodyPartEnum.values())
                if (BodyDamageUtil.getHealthRatio(player, bodyPartEnum) < 1.0f)
                    return true;
        }
        return false;
    }
}
