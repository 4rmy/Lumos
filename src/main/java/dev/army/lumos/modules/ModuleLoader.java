package dev.army.lumos.modules;

import io.github.classgraph.*;

public class ModuleLoader {

    public static void loadModules() {

        try (ScanResult scan =
                     new ClassGraph()
                             .enableClassInfo()
                             .enableAnnotationInfo()
                             .acceptPackages("dev.army.lumos.modules")
                             .scan()) {

            for (ClassInfo classInfo :
                    scan.getClassesWithAnnotation(Module.class)) {

                Class<?> clazz = classInfo.loadClass();

                if (clazz.isInterface()
                        || java.lang.reflect.Modifier.isAbstract(
                        clazz.getModifiers()
                )) {
                    continue;
                }

                Object instance =
                        clazz.getDeclaredConstructor().newInstance();

                if (instance instanceof ModuleBase module) {

                    ModuleManager.register(module);

                    System.out.println(
                            "Registered module: "
                                    + clazz.getSimpleName()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}