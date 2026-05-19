package dev.army.lumos.ui.components;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.LumosConfig;
import dev.army.lumos.ui.components.widgits.ToggleWidgit;
import dev.army.lumos.util.LumosLogger;
import net.minecraft.client.gui.DrawContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Panel extends Widgit {
    private final String name;
    ArrayList<Widgit> modules = new ArrayList<>();

    public Panel(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;

        Field category = Arrays.stream(ConfigManager.get().getClass().getFields()).filter(o -> o.getName().equals(this.name)).findFirst().get();

        for (Field f : category.getClass().getFields()) {
            if (f.getType().getFields().length == 1) {
                try {
                    modules.add(new ToggleWidgit((LumosConfig.OptionBase) f.get(category), f.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                LumosLogger.debug(f.getName() + " field count: " + f.getType().getFields().length);
            }
        }
    }

    @Override
    public int getHeight() {
        return minHeight;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks, int x, int y) {}

    public void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks) {
        ctx.fill(
                this.x,
                this.y,
                this.x + width,
                this.y + this.mc.textRenderer.fontHeight + 4,
                ConfigManager.get().General.ClickUI.primary.toARGB()
        );
        ctx.drawText(
                this.mc.textRenderer,
                this.name,
                this.x + (width/2) - this.mc.textRenderer.getWidth(this.name) / 2, // centered
                this.y + 2,
                (ConfigManager.get().General.ClickUI.primary.luminance() > 0.5) ? 0xFF000000 : 0xFFFFFFFF, // white on dark or black on light
                false
        );

        // render components
        int offset = 0;
        for (Widgit w : this.modules) {
            w.render(ctx, mouseX, mouseY, deltaTicks, this.x, this.y+this.getHeight() + offset);
            offset += w.getHeight();
        }
    }
}
