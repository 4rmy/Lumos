package dev.army.lumos.ui;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.LumosConfig;
import dev.army.lumos.ui.components.Panel;
import dev.army.lumos.ui.components.Widgit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ClickUiScreen extends Screen {
    public static final ClickUiScreen Instance = new ClickUiScreen();

    private final HashMap<String, Panel> panels = new HashMap<>();

    public ClickUiScreen() {
        super(Text.literal("ClickUiScreen"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks) {
        if (!ConfigManager.get().General.ClickUI.enabled) this.close();

        LumosConfig config = ConfigManager.get();
        Class<?> configClass = config.getClass();
        for (Field f : configClass.getDeclaredFields()) {
            // create panel if it doesn't exist.
            if (!(panels.containsKey(f.getName()))) {
                panels.put(f.getName(), new Panel(f.getName(), panels.size() * (Widgit.width+3) + 3, 3));
            }

            // render panel
            panels.get(f.getName()).render(ctx, mouseX, mouseY, deltaTicks);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.applyBlur(context);
        this.renderDarkening(context);
        this.client.inGameHud.renderDeferredSubtitles();
    }
}
