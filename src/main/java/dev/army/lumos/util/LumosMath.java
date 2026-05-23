package dev.army.lumos.util;

public class LumosMath {
    public static boolean inBounds(int x, int y, int x1, int y1, int x2, int y2) {
        return (x > x1) && (y > y1) && (x < x2) && (y < y2);
    }
}
