package dev.army.lumos.modules;

import io.github.classgraph.*;

public class ModuleLoader {

    public static void loadModules() {
        try (ScanResult scan =
                     new ClassGraph()
                             .enableClassInfo()
                             .acceptPackages("dev.army.lumos.modules.mods")
                             .scan()) {

            for (ClassInfo classInfo :
                    scan.getClassesWithAnnotation(Module.class)) {

                Class<?> clazz = classInfo.loadClass();

                Object instance =
                        clazz.getDeclaredConstructor().newInstance();

                if (instance instanceof ModuleBase module) {
                    ModuleManager.register(module);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
