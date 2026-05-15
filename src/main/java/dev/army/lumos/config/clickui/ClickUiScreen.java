package dev.army.lumos.config.clickui;

import dev.army.lumos.config.ConfigManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickUiScreen extends Screen {
    public static final ClickUiScreen Instance = new ClickUiScreen();

    public ClickUiScreen() {
        super(Text.literal("ClickUiScreen"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (!ConfigManager.get().general.enabled) this.close();

        context.fill(0, 0, 10, 10, 0xFFAA00AA);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }
}
