package dev.army.lumos.ui;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.modules.Category;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.ModuleManager;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.render.RoundedCorner;
import dev.army.lumos.ui.widgets.HeaderComponent;
import dev.army.lumos.ui.widgets.ModuleComponent;
import dev.army.lumos.ui.widgets.VerticalStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ClickUiScreen extends Screen {
    public static ClickUiScreen INSTANCE;

    public List<VerticalStack> panels = new ArrayList<>();

    public ClickUiScreen() {
        super(Text.literal(LumosClient.getModName() + " ClickUI"));

        int currentx = 4, currenty = 4;

        for (Category c : Category.values()) {
            VerticalStack stack = new VerticalStack();

            stack.setPos(currentx, currenty);
            currentx += 86; // default_width + padding*2 + spacing

            stack.add(new HeaderComponent(c.name()));

            for (ModuleBase m : ModuleManager.getByCategory(c)) {
                stack.add(new ModuleComponent(m));
            }

            panels.add(stack);
        }
    }

    @Override
    public void close() {
        ConfigManager.save();
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        LumosDrawContext ctx = new LumosDrawContext(context, mouseX, mouseY, deltaTicks);

        for (VerticalStack stack : panels) {
            stack.render(ctx);
        }

        // help menu
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        Text help_title = Text.literal("Help").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter_large"))));
        Text line11 = Text.literal("Left click ").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line12 = Text.literal("to toggle features").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line21 = Text.literal("Right click ").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line22 = Text.literal("to expand modules").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line31 = Text.literal("Click and drag ").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line32 = Text.literal("headers to move panels").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line41 = Text.literal("/lumos ").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        Text line42 = Text.literal("to open config menu").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        int height = tr.fontHeight * 4;

        ctx.drawRoundedBorder(ctx.getScaledWindowWidth() - 154, 4, ctx.getScaledWindowWidth() - 4, 20 + height + 6, 0xAA000000, 0, 4, 2, EnumSet.allOf(RoundedCorner.class));
        ctx.fillRounded(ctx.getScaledWindowWidth() - 152, 6, ctx.getScaledWindowWidth() - 6, 20, 0xCC222222, 0xCC222222, 2, true, EnumSet.of(RoundedCorner.TOP_LEFT, RoundedCorner.TOP_RIGHT));

        ctx.drawText(tr, help_title, ctx.getScaledWindowWidth() - 148, 8, 0xFFFFFFFF, false);
        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, Identifier.of(LumosClient.getModId(), "textures/ui/close.png"), ctx.getScaledWindowWidth() - 18, 9, 0, 0, 8, 8, 8, 8, 0xFFFFFFFF);

        ctx.fillRounded(ctx.getScaledWindowWidth() - 152, 20, ctx.getScaledWindowWidth() - 6, 20 + height + 4, 0xAA222222, 0xAA222222, 2, true, EnumSet.of(RoundedCorner.BOTTOM_LEFT, RoundedCorner.BOTTOM_RIGHT));

        ctx.drawText(tr, line11, ctx.getScaledWindowWidth() - 148, 22, 0xFFFFFF00, false);
        ctx.drawText(tr, line12, ctx.getScaledWindowWidth() - 148 + tr.getWidth(line11), 22, 0xFFFFFFFF, false);
        ctx.drawText(tr, line21, ctx.getScaledWindowWidth() - 148, 22 + tr.fontHeight, 0xFFFFFF00, false);
        ctx.drawText(tr, line22, ctx.getScaledWindowWidth() - 148 + tr.getWidth(line21), 22 + tr.fontHeight, 0xFFFFFFFF, false);
        ctx.drawText(tr, line31, ctx.getScaledWindowWidth() - 148, 22 + tr.fontHeight * 2, 0xFFFFFF00, false);
        ctx.drawText(tr, line32, ctx.getScaledWindowWidth() - 148 + tr.getWidth(line31), 22 + tr.fontHeight * 2, 0xFFFFFFFF, false);
        ctx.drawText(tr, line41, ctx.getScaledWindowWidth() - 148, 22 + tr.fontHeight * 3, 0xFFFFFF00, false);
        ctx.drawText(tr, line42, ctx.getScaledWindowWidth() - 148 + tr.getWidth(line41), 22 + tr.fontHeight * 3, 0xFFFFFFFF, false);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for (VerticalStack stack : panels) {
            stack.mouseClicked(click);
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        for (VerticalStack stack : panels) {
            stack.mouseReleased(click);
        }

        return super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        for (VerticalStack stack : panels) {
            stack.keyPressed(input);
        }
        return super.keyPressed(input);
    }
}
