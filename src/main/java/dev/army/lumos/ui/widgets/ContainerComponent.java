package dev.army.lumos.ui.widgets;

import dev.army.lumos.modules.settings.*;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerComponent extends Component {

    protected final List<Component> children = new ArrayList<>();

    public void add(Component component) {
        component.parent = this;
        children.add(component);
    }

    public void add(SettingValue<?> setting) {
        if (setting instanceof BooleanSetting) {
            children.add(new ToggleComponent((BooleanSetting) setting));
        } else if (setting instanceof NumberSetting) {
            children.add(new NumberComponent((NumberSetting) setting));
        } else if (setting instanceof EnumSetting<?>) {
            children.add(new EnumComponent<>((EnumSetting<?>) setting));
        } else if (setting instanceof StringSetting) {
            children.add(new StringComponent((StringSetting) setting));
        } else if (setting instanceof KeybindSetting) {
            children.add(new KeybindComponent((KeybindSetting) setting));
        } else if (setting instanceof ColorSetting) {
            children.add(new ColorComponent((ColorSetting) setting));
        }
    }

    @Override
    public void mouseClicked(Click click) {
        for (Component child : children) {
            child.mouseClicked(click);
        }
        super.mouseClicked(click);
    }

    @Override
    public void mouseReleased(Click click) {
        for (Component child : children) {
            child.mouseReleased(click);
        }
        super.mouseReleased(click);
    }

    @Override
    public void keyPressed(KeyInput input) {
        for (Component child : children) {
            child.keyPressed(input);
        }
        super.keyPressed(input);
    }
}
