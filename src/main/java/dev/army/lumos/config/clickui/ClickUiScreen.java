package dev.army.lumos.config.clickui;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.LumosConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;

public class ClickUiScreen extends Screen {
    public static final ClickUiScreen Instance = new ClickUiScreen();

    public ClickUiScreen() {
        super(Text.literal("ClickUiScreen"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks) {
        LumosConfig config = ConfigManager.get();
        Class<?> configClass = config.getClass();
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        LumosContext context = new LumosContext(ctx, mouseX, mouseY);

        int nextx = 2;
        for (Field f : configClass.getDeclaredFields()) {
            context.fillRounded(nextx, 2, nextx + tr.getWidth(f.getName()) + 4, 2 + tr.fontHeight + 4, config.general.primary.toARGB(), 0b11);
            context.drawText(tr, String.valueOf(f.getName().charAt(0)).toUpperCase() + f.getName().substring(1), nextx + 2, 4, 0xFFFFFFFF, false);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }
}
