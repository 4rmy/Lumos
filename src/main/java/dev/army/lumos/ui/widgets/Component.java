package dev.army.lumos.ui.widgets;

import dev.army.lumos.render.LumosDrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;

public abstract class Component {

    protected final int default_width = 80;
    protected final int default_radius = 2;

    protected final int padding = 2;
    protected final int base_height = 14;
    protected int x;
    protected int y;
    protected int width = default_width;
    protected int height = 14;
    protected Component parent;

    public abstract void render(LumosDrawContext ctx);

    public void layout() {
    }

    public void mouseClicked(Click click) {
    }

    public void mouseReleased(Click click) {
    }

    public void keyPressed(KeyInput input) {
    }
}
