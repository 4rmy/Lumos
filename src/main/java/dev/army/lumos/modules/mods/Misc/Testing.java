package dev.army.lumos.modules.mods.Misc;

import dev.army.lumos.modules.Category;
import dev.army.lumos.modules.Module;
import dev.army.lumos.modules.ModuleBase;
import dev.army.lumos.modules.settings.*;
import dev.army.lumos.util.types.Color;
import org.lwjgl.glfw.GLFW;

@Module(name = "Testing", category = Category.Misc)
public class Testing extends ModuleBase {
    public Testing() {
        super();

        BooleanSetting toggle = register(new BooleanSetting("Toggle", false));
        ColorSetting color = register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        EnumSetting<options> enums = register(new EnumSetting<>("Enum", options.Option1, options.values()));
        KeybindSetting keybind = register(new KeybindSetting("Keybind", GLFW.GLFW_KEY_E));
        NumberSetting number = register(new NumberSetting("Number", 0.0, 0.0, 1.0, 0.1));
        StringSetting string = register(new StringSetting("String", "def", 12));
    }

    public enum options {
        Option1,
        Option2,
        Option3
    }
}
