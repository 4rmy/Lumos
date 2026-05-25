package dev.army.lumos.modules;

public @interface Module {
    String name();
    Category category();
    boolean show_enabled() default true;
}
