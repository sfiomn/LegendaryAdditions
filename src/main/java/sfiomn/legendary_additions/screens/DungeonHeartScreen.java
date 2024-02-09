package sfiomn.legendary_additions.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.network.packets.MessageDungeonGateChange;
import sfiomn.legendary_additions.network.packets.MessageDungeonHeartRange;
import sfiomn.legendary_additions.tileentities.AbstractDungeonHeartTileEntity;

import java.util.Objects;
import java.util.function.Consumer;

public class DungeonHeartScreen extends Screen {
    public static final ResourceLocation DUNGEON_HEART_SCREEN = new ResourceLocation(LegendaryAdditions.MOD_ID, "textures/screen/dungeon_heart.png");
    public static final int DUNGEON_HEART_SCREEN_WIDTH = 252;
    public static final int DUNGEON_HEART_SCREEN_HEIGHT = 140;
    public static final int TEXT_AREA_WIDTH = 40;
    public static final int TEXT_AREA_HEIGHT = 12;
    private final AbstractDungeonHeartTileEntity tileEntity;
    private int leftPos;
    private int topPos;
    private TextFieldWidget rangeEastBox;
    private TextFieldWidget rangeWestBox;
    private TextFieldWidget rangeUpBox;
    private TextFieldWidget rangeDownBox;
    private TextFieldWidget rangeSouthBox;
    private TextFieldWidget rangeNorthBox;

    public DungeonHeartScreen(AbstractDungeonHeartTileEntity tileEntity) {
        super(new TranslationTextComponent("screen." + LegendaryAdditions.MOD_ID + ".dungeon_heart"));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

        this.leftPos = (this.width - DUNGEON_HEART_SCREEN_WIDTH) /2;
        this.topPos = (this.height - DUNGEON_HEART_SCREEN_HEIGHT) /2;

        this.rangeEastBox = addTextBox(50, 29, this.tileEntity.getRangeXPos(), setRangeValue(this.rangeEastBox, Direction.EAST));
        this.rangeWestBox = addTextBox(164, 29, this.tileEntity.getRangeXNeg(), setRangeValue(this.rangeWestBox, Direction.WEST));
        this.rangeUpBox = addTextBox(50, 66, this.tileEntity.getRangeYPos(), setRangeValue(this.rangeUpBox, Direction.UP));
        this.rangeDownBox = addTextBox(164, 66, this.tileEntity.getRangeYNeg(), setRangeValue(this.rangeDownBox, Direction.DOWN));
        this.rangeSouthBox = addTextBox(50, 101, this.tileEntity.getRangeZPos(), setRangeValue(this.rangeSouthBox, Direction.SOUTH));
        this.rangeNorthBox = addTextBox(164, 101, this.tileEntity.getRangeZNeg(), setRangeValue(this.rangeNorthBox, Direction.NORTH));
    }

    private TextFieldWidget addTextBox(int posX, int posY, int defaultValue, Consumer<String> c) {
        TextFieldWidget textBox = new TextFieldWidget(this.font, this.leftPos + posX, this.topPos + posY, TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT, new StringTextComponent("X Y Z"));
        textBox.setCanLoseFocus(true);
        textBox.setTextColor(-1);
        textBox.setTextColorUneditable(-1);
        textBox.setBordered(false);
        textBox.setMaxLength(5);
        textBox.setResponder(c);
        try {
            textBox.setValue(String.valueOf(defaultValue));
        } catch (NumberFormatException e) {
            textBox.setValue("0");
        }
        this.addWidget(textBox);
        return textBox;
    }

    private Consumer<String> setRangeValue(TextFieldWidget textBox, Direction direction) {
        return (String s) -> {
            while (!isInt(s))
                textBox.deleteChars(1);
            if (s.length() == 0) {
                this.tileEntity.setRange(direction, 0);
                this.sendUpdateRange(direction, 0);
            } else {
                this.tileEntity.setRange(direction, Integer.parseInt(s));
                this.sendUpdateRange(direction, Integer.parseInt(s));
            }
        };
    }

    private boolean isInt(String s) {
        if (s.length() == 0)
            return true;
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void sendUpdateRange(Direction rangeDirection, int rangeValue) {
        CompoundNBT updateRangeNbt = new CompoundNBT();
        updateRangeNbt.putInt("posX", this.tileEntity.getBlockPos().getX());
        updateRangeNbt.putInt("posY", this.tileEntity.getBlockPos().getY());
        updateRangeNbt.putInt("posZ", this.tileEntity.getBlockPos().getZ());
        updateRangeNbt.putString("rangeDirection", rangeDirection.getName());
        updateRangeNbt.putInt("rangeValue", rangeValue);
        MessageDungeonHeartRange messageDungeonHeartChange = new MessageDungeonHeartRange(updateRangeNbt);
        NetworkHandler.INSTANCE.sendToServer(messageDungeonHeartChange);
    }

    @Override
    public boolean keyPressed(int id, int p_231046_2_, int p_231046_3_) {
        if (id == 256) {
            assert Objects.requireNonNull(this.minecraft).player != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
        }

        return this.rangeEastBox.keyPressed(id, p_231046_2_, p_231046_3_) || this.rangeWestBox.keyPressed(id, p_231046_2_, p_231046_3_) ||
                this.rangeUpBox.keyPressed(id, p_231046_2_, p_231046_3_) || this.rangeDownBox.keyPressed(id, p_231046_2_, p_231046_3_) ||
                this.rangeSouthBox.keyPressed(id, p_231046_2_, p_231046_3_) || this.rangeNorthBox.keyPressed(id, p_231046_2_, p_231046_3_) ||
                super.keyPressed(id, p_231046_2_, p_231046_3_);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderFg(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void renderFg(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.rangeEastBox.render(matrix, mouseX, mouseY, partialTicks);
        this.rangeWestBox.render(matrix, mouseX, mouseY, partialTicks);
        this.rangeUpBox.render(matrix, mouseX, mouseY, partialTicks);
        this.rangeDownBox.render(matrix, mouseX, mouseY, partialTicks);
        this.rangeSouthBox.render(matrix, mouseX, mouseY, partialTicks);
        this.rangeNorthBox.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        if (minecraft == null) {
            return;
        }

        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(DUNGEON_HEART_SCREEN);

        blit(matrixStack, leftPos, topPos, 0, 0, DUNGEON_HEART_SCREEN_WIDTH, DUNGEON_HEART_SCREEN_HEIGHT);
    }
}
