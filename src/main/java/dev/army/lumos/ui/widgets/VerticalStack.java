package dev.army.lumos.ui.widgets;

import dev.army.lumos.render.LumosDrawContext;
import dev.army.lumos.render.RoundedCorner;
import dev.army.lumos.util.LumosMath;
import net.minecraft.client.gui.Click;

import java.util.EnumSet;

public class VerticalStack extends ContainerComponent {
    private boolean mouseDown = false;
    private float offX = 0, offY = 0;

    @Override
    public void layout() {
        width = default_width + padding * 2;
        int currentY = y + padding;

        for (Component child : children) {
            child.x = x + padding;
            child.y = currentY;
            child.width = width - padding * 2;
            child.layout();
            currentY += child.height;
        }

        height = currentY - y + padding;
    }

    @Override
    public void render(LumosDrawContext ctx) {
        if (mouseDown) {
            x = (int) (ctx.getMouseX() - offX);
            y = (int) (ctx.getMouseY() - offY);
        }
        layout();

        ctx.drawRoundedBorder(x, y, x + width, y + height, 0xCC000000, 0, default_radius + padding, padding, EnumSet.of(RoundedCorner.TOP_LEFT, RoundedCorner.TOP_RIGHT));

        for (Component child : children) {
            child.render(ctx);
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void mouseClicked(Click click) {
        layout();
        if (click.button() == 0 && children.getFirst() != null && LumosMath.inBounds((int) click.x(), (int) click.y(), children.getFirst().x, children.getFirst().y, children.getFirst().x + children.getFirst().width, children.getFirst().y + children.getFirst().height)) {
            mouseDown = true;
            offX = (float) (click.x() - x);
            offY = (float) (click.y() - y);
        }

        for (Component child : children) {
            child.mouseClicked(click);
        }
    }

    @Override
    public void mouseReleased(Click click) {
        mouseDown = false;

        for (Component child : children) {
            child.mouseReleased(click);
        }
    }
}
