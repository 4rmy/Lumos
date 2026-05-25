package dev.army.lumos.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.army.lumos.ui.ClickUiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class LumosCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("lumos").executes(LumosCommands::root).then(CommandManager.literal("config").executes(LumosCommands::config).then(CommandManager.literal("reload").executes(LumosCommands::reload)).then(CommandManager.literal("reset").executes(LumosCommands::reset))));
    }

    private static int reset(CommandContext<ServerCommandSource> ctx) {
        //ConfigManager.reset();
        ctx.getSource().sendFeedback(() -> Text.literal("Lumos config reset.").withColor(0xFFFF4242), false);
        return 1;
    }

    private static int reload(CommandContext<ServerCommandSource> ctx) {
        //ConfigManager.load();
        ctx.getSource().sendFeedback(() -> Text.literal("Lumos config reloaded.").withColor(// argb
                0xFFFF00FF), false);
        return 1;
    }

    private static int config(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.literal("Manage the lumos config.").withColor(// argb
                0xFFFF00FF), false);
        return 1;
    }

    // commands
    private static int root(CommandContext<ServerCommandSource> ctx) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> client.setScreen(ClickUiScreen.Instance));
        return 1;
    }
}
