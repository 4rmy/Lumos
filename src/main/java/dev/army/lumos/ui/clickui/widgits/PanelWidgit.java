package dev.army.lumos.ui.clickui.widgits;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import dev.army.lumos.util.LumosReflector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class PanelWidgit extends Widgit {
    private final String name;
    private final ArrayList<ModuleWidgit> mods = new ArrayList<>();

    public PanelWidgit(String field_name, int x, int y) {
        super(x, y, field_name);
        this.name = field_name.replace('_', ' ');

        LumosReflector instance = (LumosReflector) ConfigManager.get().getChild(field_name);

        int ty = this.y + Widgit.minHeight;
        for (String mod : instance.getChildren()) {
            mods.add(new ModuleWidgit(mod, this.x, ty, this.getPath(), this));
            ty += Widgit.minHeight;
        }

    }

    @Override
    int getHeight() {
        int height = Widgit.minHeight;
        for (ModuleWidgit m : mods) {
            height += m.getHeight();
        }
        return height;
    }

    @Override
    public void render(LumosDrawContext ctx, int mouseX, int mouseY, float deltaTicks) {
        // panel header
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        ctx.fillRounded(this.x, this.y, this.x + Widgit.width, this.y + Widgit.minHeight, 0xFF151515, 4);
        ctx.fill(this.x, this.y + Widgit.minHeight - 4, this.x + Widgit.width, this.y + Widgit.minHeight, 0xFF151515);
        Text name = Text.literal(this.name).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter_large"))));
        ctx.drawText(tr, name, this.x + Widgit.width / 2 - tr.getWidth(name) / 2, 4, 0xFFFFFFFF, false);

        // widgits
        for (ModuleWidgit m : mods) {
            m.render(ctx, mouseX, mouseY, deltaTicks);
        }
    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {
        for (ModuleWidgit m : mods) {
            m.mouseClicked(click, doubled);
        }
    }

    public void update() {
        int y = this.y + Widgit.minHeight;
        for (ModuleWidgit m : mods) {
            m.setX(this.x);
            m.setY(y);
            y += m.getHeight();
        }
    }
}
