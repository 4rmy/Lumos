package dev.army.lumos.ui;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.ui.clickui.ClickUI;
import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickUiScreen extends Screen {
    public static final ClickUiScreen Instance = new ClickUiScreen();

    public ClickUiScreen() {
        super(Text.literal(LumosClient.getModName() + " ClickUI"));
    }

    @Override
    public void close() {
        //ConfigManager.save();
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (ClickUI.Instance == null) {
            ClickUI.Instance = new ClickUI();
        }

        ClickUI.Instance.render(new LumosDrawContext(context, mouseX, mouseY), mouseX, mouseY, deltaTicks);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        return ClickUI.Instance.mouseClicked(click, doubled);
    }
}
