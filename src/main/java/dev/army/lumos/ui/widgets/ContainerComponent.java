package dev.army.lumos.ui.widgets;

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
