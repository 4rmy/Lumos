package dev.army.lumos.util;

import dev.army.lumos.config.ConfigManager;
import dev.army.lumos.config.LumosConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class LumosReflector {
    /**
     * Returns the names of all fields whose types are public static classes.
     */
    public String[] getChildren() {
        List<String> children = new ArrayList<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            Class<?> type = field.getType();

            // check if the field type is a public static inner class
            if (type.isMemberClass() && Modifier.isStatic(type.getModifiers()) && Modifier.isPublic(type.getModifiers())) {

                children.add(field.getName());
            }
        }

        return children.toArray(new String[0]);
    }

    /**
     * Returns the object instance of a child field by name.
     */
    public Object getChild(String name) {
        try {
            Field field = this.getClass().getDeclaredField(name);

            Class<?> type = field.getType();

            // make sure it's a valid child type
            if (type.isMemberClass() && Modifier.isStatic(type.getModifiers()) && Modifier.isPublic(type.getModifiers())) {

                field.setAccessible(true);
                return field.get(this);
            }

        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return null;
    }

    public static ObjectPair fromPath(LumosConfig config, String path) {
        try {
            String[] parts = path.split("\\.");

            Object current = config;
            Class<?> currentClass = current.getClass();

            for (String part : parts) {

                Field field = currentClass.getDeclaredField(part);
                field.setAccessible(true);

                current = field.get(current);

                if (current == null) {
                    return new ObjectPair(field.getType(), null);
                }

                currentClass = current.getClass();
            }

            return new ObjectPair(currentClass, current);
        } catch (Exception e) {
            System.err.println("Invalid Path: " + path);
            throw new RuntimeException(e);
        }
    }
}
