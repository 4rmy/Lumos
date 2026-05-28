package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.BooleanSetting;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ToggleComponent extends Component {
    private final BooleanSetting setting;
    private final Text name;

    public ToggleComponent(BooleanSetting setting) {
        this.setting = setting;
        name = Text.literal(setting.getName()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
    }

    @Override
    public void render(LumosDrawContext ctx) {
        ctx.fill(x, y, x + width, y + height, 0x55555555);
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        ctx.drawText(tr, name, x + padding, y + padding, -1, false);
        // switch bg
        ctx.fillRounded(x + width - padding - width / 5, y + height / 4, x + width - padding, y + height / 4 * 3, (setting.get()) ? 0x55AA66FF : 0x55222222, 1);
        // switch btn
        if (setting.get()) {
            ctx.fillRounded(
                    x + width - padding - height / 2 + 1,
                    y + height / 4,
                    x + width - padding,
                    y + height / 4 * 3,
                    0xFFFFFFFF,
                    1
            );
        } else {
            ctx.fillRounded(
                    x + width - padding - width / 5,
                    y + height / 4,
                    x + width - padding - width / 5 + height / 2 - 1,
                    y + height / 4 * 3,
                    0xFFFFFFFF,
                    1
            );
        }
    }

    @Override
    public void mouseClicked(Click click) {
        if (click.button() == 0 && LumosMath.inBounds(
                (int) click.x(), (int) click.y(),
                x + width - padding - width / 5, y + height / 4, x + width - padding, y + height / 4 * 3
        )) {
            setting.toggle();
        }
    }
}
