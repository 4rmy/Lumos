package dev.army.lumos.ui.components.widgits;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.LumosConfig;
import dev.army.lumos.ui.components.Widgit;
import net.minecraft.client.gui.DrawContext;

public class DropDownWidgit extends Widgit{
    private LumosConfig.OptionBase option;
    private String name;

    public DropDownWidgit(LumosConfig.OptionBase option, String name) {
        this.option = option;
        this.name = name;
    }

    @Override
    public int getHeight() {
        return Widgit.minHeight;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float deltaTicks, int x, int y) {
        ctx.fill(
                x,
                y,
                x + Widgit.width,
                y + this.getHeight(),
                (this.option.enabled) ? ConfigManager.get().General.ClickUI.primary.toARGB() : ConfigManager.get().General.ClickUI.disabled.toARGB()
        );
    }
}
