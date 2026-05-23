package dev.army.lumos.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LumosModuleReflector {
    public static List<OptionPair> getChildren(Object parent) throws IllegalAccessException {
        List<OptionPair> result = new ArrayList<>();

        collectChildren(parent, "", result);

        return result;
    }

    private static void collectChildren(Object obj, String path, List<OptionPair> out) throws IllegalAccessException {

        if (obj == null) return;

        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            Object value = field.get(obj);

            String name = path.isEmpty() ? field.getName() : path + "." + field.getName();

            Class<?> type = field.getType();

            // primitive / leaf values
            if (type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class || type.isEnum() || type.getPackageName().startsWith("dev.army.lumos.util")) {
                out.add(new OptionPair(type, name));
            }

            // recurse into config objects
            else {
                collectChildren(value, name, out);
            }
        }
    }
}
