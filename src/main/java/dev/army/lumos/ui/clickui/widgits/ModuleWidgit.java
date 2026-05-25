package dev.army.lumos.ui.clickui.widgits;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.ModuleBase;
import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import dev.army.lumos.util.LumosReflector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModuleWidgit extends Widgit {
    private final String name;
    private final PanelWidgit parent;
    private boolean expanded = false;

    public ModuleWidgit(String name, int x, int y, String path, PanelWidgit parent) {
        super(x, y, path + '.' + name);
        this.name = name;
        this.parent = parent;
    }

    @Override
    int getHeight() {
        return Widgit.minHeight;
    }

    @Override
    public void render(LumosDrawContext ctx, int mouseX, int mouseY, float deltaTicks) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        Text name = Text.literal(this.name.replace("_", " ")).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        try {
            ctx.fill(this.x, this.y, this.x + Widgit.width, this.y + this.getHeight(), (((ModuleBase) LumosReflector.fromPath(ConfigManager.get(), this.path).value()).enabled) ? 0xBFBF77BF : 0xBF333333);
        } catch (ClassCastException e) {
            ctx.fill(this.x, this.y, this.x + Widgit.width, this.y + this.getHeight(), 0xBF333333);
        }
        ctx.drawText(tr, name, this.x + 2, this.y + 2, 0xFFFFFFFF, false);
        Identifier texture = Identifier.of(LumosClient.getModId(), "textures/ui/dropdown" + ((this.expanded) ? "_rotated" : "") + ".png");
        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, texture, this.x + Widgit.width - Widgit.minHeight, this.y, 0, 0, Widgit.minHeight, Widgit.minHeight, Widgit.minHeight, Widgit.minHeight, -1);
    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {
        if (LumosMath.inBounds((int) click.x(), (int) click.y(), this.x, this.y, this.x + Widgit.width, this.y + Widgit.minHeight)) {
            if (click.button() == 1) {
                this.expanded = !this.expanded;
                this.parent.update();
            }
        }
    }
}
