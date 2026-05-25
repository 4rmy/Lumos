package dev.army.lumos.modules;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<ModuleBase> modules = new ArrayList<>();

    public static void register(ModuleBase module) {
        modules.add(module);
    }

    public static List<ModuleBase> getModules() {
        return modules;
    }

    public static List<ModuleBase> getByCategory(Category category) {
        return modules.stream().filter(m -> {
            Module annotation = m.getClass().getAnnotation(Module.class);
            return annotation.category() == category;
        }).toList();
    }
}
