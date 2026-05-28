package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.NumberSetting;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NumberComponent extends Component {

    private final NumberSetting setting;
    private final Text name;

    private boolean dragging = false;

    public NumberComponent(NumberSetting setting) {
        this.setting = setting;
        this.name = Text.literal(setting.getName())
                .setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(
                        Identifier.of(LumosClient.getModId(), "inter")
                )));

        height = 22;
    }

    @Override
    public void render(LumosDrawContext ctx) {

        ctx.fill(x, y, x + width, y + height, 0x55555555);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        ctx.drawText(tr, name, x + padding, y + padding, -1, false);

        Text value = Text.literal(setting.get().toString()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        ctx.drawText(tr, value,
                x + width - padding - tr.getWidth(value),
                y + padding,
                -1,
                false
        );

        int sliderX1 = x + padding;
        int sliderX2 = x + width - padding;

        int sliderY1 = y + height - 8;
        int sliderY2 = sliderY1 + 4;

        double percent = (setting.get() - setting.getMin()) /
                (setting.getMax() - setting.getMin());

        int fillX = sliderX1 + (int) ((sliderX2 - sliderX1) * percent);

        ctx.fillRounded(sliderX1, sliderY1, sliderX2, sliderY2, 0x55222222, 2);
        if (fillX > sliderX1) {
            ctx.fillRounded(
                    sliderX1,
                    sliderY1,
                    fillX,
                    sliderY2,
                    0x55AA66FF,
                    2
            );
        }

        if (dragging) {
            updateValue(ctx.getMouseX(), sliderX1, sliderX2);
        }
    }

    private void updateValue(int mouseX, int start, int end) {

        double percent = (mouseX - start) / (double) (end - start);
        percent = Math.clamp(percent, 0.0, 1.0);

        double value = setting.getMin() +
                percent * (setting.getMax() - setting.getMin());

        setting.set(value);
    }

    @Override
    public void mouseClicked(Click click) {

        int sliderY1 = y + height - 8;

        if (click.button() == 0 && LumosMath.inBounds(
                (int) click.x(), (int) click.y(),
                x + padding,
                sliderY1,
                x + width - padding,
                sliderY1 + 6
        )) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(Click click) {
        dragging = false;
    }
}