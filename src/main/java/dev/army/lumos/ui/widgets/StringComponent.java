package dev.army.lumos.ui.widgets;

import dev.army.lumos.client.LumosClient;
import dev.army.lumos.modules.settings.StringSetting;
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

public class StringComponent extends Component {

    private final StringSetting setting;
    private final Text name;

    private boolean focused = false;

    public StringComponent(StringSetting setting) {
        this.setting = setting;

        this.name = Text.literal(setting.getName()).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));

        height = 28;
    }

    @Override
    public void render(LumosDrawContext ctx) {

        ctx.fill(x, y, x + width, y + height, 0x55555555);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        ctx.drawText(tr, name, x + padding, y + padding, -1, false);

        int boxY = y + 14;

        ctx.fillRounded(x + padding, boxY, x + width - padding, boxY + 10, focused ? 0x55AA66FF : 0x55222222, 2);

        Text text = Text.literal(setting.get() + (focused ? "_" : "")).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of(LumosClient.getModId(), "inter"))));
        ctx.drawText(tr, text, x + padding + 3, boxY + 1, -1, false);
    }

    @Override
    public void mouseClicked(Click click) {
        focused = click.button() == 0 && LumosMath.inBounds((int) click.x(), (int) click.y(), x + padding, y + 14, x + width - padding, y + 24);
    }

    @Override
    public void keyPressed(KeyInput input) {

        if (!focused) {
            return;
        }

        if (input.getKeycode() == 259) {

            if (!setting.get().isEmpty()) {
                setting.set(setting.get().substring(0, setting.get().length() - 1));
            }

            return;
        } else if (input.getKeycode() == GLFW.GLFW_KEY_SPACE) {
            setting.set(setting.get() + " ");
        }

        String character = GLFW.glfwGetKeyName(input.key(), input.scancode());

        assert character != null;
        if (!character.isBlank()) {
            setting.set(setting.get() + character);
        }
    }
}
