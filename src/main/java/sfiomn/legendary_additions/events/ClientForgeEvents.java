package sfiomn.legendary_additions.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.screens.ResetTradeButton;

@Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void preRenderScreen(ScreenEvent.Render.Pre event) {
        if (Config.Baked.resetTradeEnabled && event.getScreen() instanceof MerchantScreen merchantScreen) {
            ResetTradeButton buttonRT = null;
            for (GuiEventListener button: merchantScreen.children()) {
                if (button instanceof ResetTradeButton resetTradeButton) {
                    resetTradeButton.updatePosition(merchantScreen.getGuiLeft(), merchantScreen.getGuiTop());
                    resetTradeButton.checkEnabled();
                    if (resetTradeButton.hasVillagerXp())
                        buttonRT = resetTradeButton;
                }
            }

            if (buttonRT != null) {
                buttonRT.active = false;
                buttonRT.visible = false;
                merchantScreen.children().remove(buttonRT);
            }
        }
    }

    @SubscribeEvent
    public static void screenInitPost(ScreenEvent.Init.Post event) {
        Player player = Minecraft.getInstance().player;
        if (Config.Baked.resetTradeEnabled && player != null && event.getScreen() instanceof MerchantScreen merchantScreen) {
            event.addListener(new ResetTradeButton(Minecraft.getInstance().player, merchantScreen.getMenu(), merchantScreen.getGuiLeft(), merchantScreen.getGuiTop()));
        }
    }
}
