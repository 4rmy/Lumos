package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.ColorSetting;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import dev.army.lumos.util.types.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ColorComponent extends Component {

    private final ColorSetting setting;
    private final Text name;

    private boolean expanded = false;

    private boolean draggingSV = false;
    private boolean draggingHue = false;

    public ColorComponent(ColorSetting setting) {
        this.setting = setting;

        this.name = Text.literal(setting.getName()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        height = base_height;
    }

    @Override
    public void render(LumosDrawContext ctx) {
        height = expanded ? 74 : base_height;

        ctx.fill(x, y, x + width, y + height, 0x55555555);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        ctx.drawText(tr, name, x + padding, y + padding, -1, false);

        Color color = setting.get();

        // preview
        ctx.fillRounded(x + width - 18, y + 3, x + width - 4, y + height - (expanded ? 58 : 3), color.getARGB(), 2);

        if (!expanded) {
            return;
        }

        int svX = x + padding;
        int svY = y + 18;

        int svSize = 42;

        int hueX = svX + svSize + 6;
        int hueWidth = 8;

        // saturation/value square
        for (int ix = 0; ix < svSize; ix++) {

            float saturation = ix / (float) (svSize - 1);

            for (int iy = 0; iy < svSize; iy++) {

                float value = 1f - (iy / (float) (svSize - 1));

                int rgb = java.awt.Color.HSBtoRGB(color.getHue() / 360f, saturation, value);

                ctx.fill(svX + ix, svY + iy, svX + ix + 1, svY + iy + 1, 0xFF000000 | rgb);
            }
        }

        // hue slider
        for (int iy = 0; iy < svSize; iy++) {

            float hue = iy / (float) (svSize - 1);

            int rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f);

            ctx.fill(hueX, svY + iy, hueX + hueWidth, svY + iy + 1, 0xFF000000 | rgb);
        }

        // sv cursor
        float sat = Math.clamp(color.getSaturation(), 0f, 1f);
        float val = Math.clamp(color.getValue(), 0f, 1f);

        int svCursorX = svX + (int) (sat * svSize);
        int svCursorY = svY + (int) ((1f - val) * svSize);

        ctx.fill(svCursorX - 1, svCursorY - 1, svCursorX + 2, svCursorY + 2, 0xFFFFFFFF);

        // hue cursor
        float hue = Math.clamp(color.getHue(), 0f, 360f);

        int hueCursorY = svY + (int) ((hue / 360f) * svSize);

        ctx.fill(hueX - 1, hueCursorY - 1, hueX + hueWidth + 1, hueCursorY + 1, 0xFFFFFFFF);

        // dragging sv
        if (draggingSV) {

            float saturation = (ctx.getMouseX() - svX) / (float) svSize;
            float value = 1f - ((ctx.getMouseY() - svY) / (float) svSize);

            saturation = Math.clamp(saturation, 0f, 1f);
            value = Math.clamp(value, 0f, 1f);

            color.setSaturation(saturation);
            color.setValue(value);
        }

        // dragging hue
        if (draggingHue) {

            hue = ((ctx.getMouseY() - svY) / (float) svSize) * 360f;

            hue = Math.clamp(hue, 0f, 360f);

            color.setHue(hue);
        }
    }

    @Override
    public void mouseClicked(Click click) {

        // expand
        if (click.button() == 1 && LumosMath.inBounds((int) click.x(), (int) click.y(), x, y, x + width, y + base_height)) {

            expanded = !expanded;
            return;
        }

        if (!expanded) {
            return;
        }

        int svX = x + padding;
        int svY = y + 18;

        int svSize = 42;

        int hueX = svX + svSize + 6;

        draggingSV = LumosMath.inBounds((int) click.x(), (int) click.y(), svX, svY, svX + svSize, svY + svSize);

        draggingHue = LumosMath.inBounds((int) click.x(), (int) click.y(), hueX, svY, hueX + 8, svY + svSize);
    }

    @Override
    public void mouseReleased(Click click) {

        draggingSV = false;
        draggingHue = false;
    }
}