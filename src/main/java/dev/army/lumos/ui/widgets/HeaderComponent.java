package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.render.LumosDrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HeaderComponent extends Component {
    private final Text header;

    public HeaderComponent(String header) {
        this.header = Text.literal(header).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter_large"))));
    }

    @Override
    public void render(LumosDrawContext ctx) {
        ctx.fillRounded(x, y, x + width, y + height, 0xFF222222, default_radius);
        ctx.fill(x, y + height / 2, x + width, y + height, 0xFF222222);
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        ctx.drawText(
                tr,
                header,
                x + width/2 - tr.getWidth(header) / 2,
                y + padding,
                0xFFFFFFFF,
                false
        );
    }
}
