package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.EnumSetting;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EnumComponent<T extends Enum<T>> extends Component {

    private final EnumSetting<T> setting;
    private final Text name;

    public EnumComponent(EnumSetting<T> setting) {
        this.setting = setting;
        this.name = Text.literal(setting.getName())
                .setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(
                        Identifier.of(LumosClient.getModId(), "inter")
                )));
    }

    @Override
    public void render(LumosDrawContext ctx) {

        ctx.fill(x, y, x + width, y + height, 0x55555555);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        ctx.drawText(tr, name, x + padding, y + padding, -1, false);

        Text value = Text.literal(setting.get().name()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        int textWidth = tr.getWidth(value);

        int boxWidth = Math.max(32, textWidth + 10);
        int boxX = x + width - padding - boxWidth;

        ctx.fillRounded(boxX, y + 2, boxX + boxWidth, y + height - 2, 0x55222222, 2);

        ctx.drawText(tr, value,
                boxX + (boxWidth - textWidth) / 2,
                y + (height - tr.fontHeight) / 2,
                -1,
                false
        );
    }

    @Override
    public void mouseClicked(Click click) {
        if (click.button() == 0 && LumosMath.inBounds(
                (int) click.x(), (int) click.y(),
                x, y, x + width, y + height
        )) {
            setting.next();
        }
    }
}