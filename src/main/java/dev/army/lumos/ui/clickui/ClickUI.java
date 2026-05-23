package dev.army.lumos.ui.clickui;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.ui.clickui.render.LumosDrawContext;
import dev.army.lumos.ui.clickui.widgits.PanelWidgit;
import net.minecraft.client.gui.Click;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClickUI {
    public static ClickUI Instance = null;
    private final HashMap<String, PanelWidgit> panels = new HashMap<>();

    public ClickUI() {
        // load config
        String[] categories = ConfigManager.get().getChildren();

        int x = 2;
        for (String c : categories) {
            panels.put(c, new PanelWidgit(c, x, 2));
            x += 103;
        }
    }

    public void render(LumosDrawContext context, int mouseX, int mouseY, float deltaTicks) {
        // render panels
        for (PanelWidgit p : panels.values()) {
            p.render(context, mouseX, mouseY, deltaTicks);
        }
    }

    public boolean mouseClicked(Click click, boolean doubled) {
        // pass to widgits
        for (PanelWidgit p : panels.values()) {
            p.mouseClicked(click, doubled);
        }

        return true;
    }
}
