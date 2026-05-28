package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.KeybindSetting;
import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeybindComponent extends Component {

    private final KeybindSetting setting;
    private final Text name;

    private boolean listening = false;

    public KeybindComponent(KeybindSetting setting) {
        this.setting = setting;
        this.name = Text.literal(setting.getName()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
    }

    @Override
    public void render(LumosDrawContext ctx) {

        ctx.fill(x, y, x + width, y + height, 0x55555555);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        ctx.drawText(tr, name, x + padding, y + padding, -1, false);

        String t = (listening ? "..." : GLFW.glfwGetKeyName(setting.get(), -1));
        Text keyName = Text.literal((t != null) ? t : "null").setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        int textWidth = tr.getWidth(keyName);
        int boxWidth = Math.max(40, textWidth + 10);

        int boxX = x + width - padding - boxWidth;

        ctx.fillRounded(boxX, y + 2, boxX + boxWidth, y + height - 2, listening ? 0x558844FF : 0x55222222, 2);

        ctx.drawText(tr, keyName, boxX + (boxWidth - textWidth) / 2, y + (height - tr.fontHeight) / 2, -1, false);
    }

    @Override
    public void mouseClicked(Click click) {
        listening = click.button() == 0 && LumosMath.inBounds((int) click.x(), (int) click.y(), x, y, x + width, y + height);
    }

    @Override
    public void keyPressed(KeyInput input) {

        if (!listening) {
            return;
        }

        setting.set(input.getKeycode());
        listening = false;
    }
}