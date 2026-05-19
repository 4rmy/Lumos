package dev.army.lumos.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickUiScreen extends Screen {
    public static final ClickUiScreen Instance = new ClickUiScreen();

    public ClickUiScreen() {
        super(Text.literal("Lumos ClickGUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }
}
