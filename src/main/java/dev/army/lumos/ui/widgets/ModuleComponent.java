package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.settings.SettingValue;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModuleComponent extends ContainerComponent {
    private final ModuleBase module;
    private final Text name;

    private boolean expanded = false;

    public ModuleComponent(ModuleBase module) {
        this.module = module;
        this.name = Text.literal(module.getMetadata().name()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        for (SettingValue<?> setting : module.getSettings()) {
            add(setting);
        }
    }

    @Override
    public void layout() {
        if (!expanded) {
            this.height = base_height;
        } else {
            int h = base_height;
            for (Component c : children) {
                c.x = x;
                c.y = y + h;
                c.layout();
                h += c.height;
            }
            this.height = h;
        }
    }

    @Override
    public void render(LumosDrawContext ctx) {
        ctx.fill(x, y, x + width, y + base_height, (module.getMetadata().hide_enabled() || !module.isEnabled()) ? 0xAA333333 : 0x33FF33FF);
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        ctx.drawText(tr, name, x + padding, y + padding, -1, false);
        if (!children.isEmpty())
            ctx.drawTexture(RenderPipelines.GUI_TEXTURED, Identifier.of(LumosClient.getModId(), "textures/ui/dropdown" + ((this.expanded) ? "_rotated" : "") + ".png"), x + width - base_height / 2 - 5, y + base_height / 2 - 5, 0, 0, 10, 10, 10, 10, 0xFFFFFFFF);
        if (this.expanded) {
            for (Component c : children) {
                c.render(ctx);
            }
        }
    }

    @Override
    public void mouseClicked(Click click) {
        if (LumosMath.inBounds((int) click.x(), (int) click.y(), x, y, x + width, y + base_height)) {
            if (click.button() == 0 && !module.getMetadata().hide_enabled()) {
                module.toggle();
            } else if (click.button() == 1) {
                this.expanded = !this.expanded;
            }
        }

        for (Component c : children) {
            c.mouseClicked(click);
        }
    }
}
