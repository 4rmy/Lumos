package dev.army.lumos.modules;

import dev.army.lumos.modules.mods.Misc.Another;
import dev.army.lumos.modules.mods.Misc.Testing;
import dev.army.lumos.util.LumosLogger;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<ModuleBase> modules = new ArrayList<>();

    public static void register(ModuleBase module) {
        LumosLogger.info("Registered new module: " + module.getMetadata().name());
        modules.add(module);
    }

    public static List<ModuleBase> getModules() {
        return List.copyOf(modules);
    }

    public static List<ModuleBase> getByCategory(Category category) {
        return getModules().stream().filter(m -> m.getMetadata().category() == category).toList();
    }

    @SuppressWarnings("unchecked")
    public static <T extends ModuleBase> T get(Class<T> clazz) {
        return (T) modules.stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }

    @SuppressWarnings("unused")
    public static void load() {
        Testing testing = new Testing();
        Another another = new Another();
    }
}
