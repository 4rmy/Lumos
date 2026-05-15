package dev.army.lumos.util;


import dev.army.lumos.client.LumosClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LumosLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(LumosClient.getModName());
    private static boolean DEBUG_ENABLED = true;

    public static void setDebug(boolean enabled) {
        DEBUG_ENABLED = enabled;
    }

    public static void info(String message) {
        LOGGER.info("{}", message);
    }

    public static void warn(String message) {
        LOGGER.warn("{}", message);
    }

    public static void error(String message) {
        LOGGER.error("{}", message);
    }

    public static void error(String message, Throwable t) {
        LOGGER.error("{}", message, t);
    }

    public static void debug(String message) {
        if (!DEBUG_ENABLED) return;
        LOGGER.info("[Lumos][DEBUG] {}", message);
    }
}
