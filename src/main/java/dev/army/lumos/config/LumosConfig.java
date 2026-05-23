package dev.army.lumos.config;

import dev.army.lumos.util.LumosReflector;
import dev.army.lumos.util.Types.Color4;

@SuppressWarnings("unused")
public class LumosConfig extends LumosReflector {
    public final general General = new general();
    public final render Render = new render();
    public final inventory Inventory = new inventory();
    public final mining Mining = new mining();
    public final farming Farming = new farming();
    public final dungeons Dungeons = new dungeons();
    public final slayers Slayers = new slayers();
    public final kuudra Kuudra = new kuudra();
    public boolean debug = false;
    public boolean showHelp = true;

    // category classes
    public static class general extends LumosReflector {
        public final snap_tap Snap_Tap = new snap_tap();
        public final mod_options Mod_Options = new mod_options();

        // modules
        public static class snap_tap extends ModuleBase {
        }

        public static class mod_options {
            public Color4 primary = Color4.from(0xFFFF00FF);
        }
    }

    public static class render extends LumosReflector {
    }

    public static class inventory extends LumosReflector {
        public final colored_enchants Colored_Enchants = new colored_enchants();
        public final item_value Item_Value = new item_value();
        public final rarity_highlight Rarity_Highlight = new rarity_highlight();
        public final missing_enchants Missing_Enchants = new missing_enchants();

        // modules
        public static class colored_enchants extends ModuleBase {
        }

        public static class item_value extends ModuleBase {
        }

        public static class rarity_highlight extends ModuleBase {
        }

        public static class missing_enchants extends ModuleBase {
        }
    }

    public static class mining extends LumosReflector {
    }

    public static class farming extends LumosReflector {
    }

    public static class dungeons extends LumosReflector {
    }

    public static class slayers extends LumosReflector {
    }

    public static class kuudra extends LumosReflector {
    }

    // base module class

}