package io.github.techdweebgaming.modularjda.api.logger;

import org.jetbrains.annotations.Contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

public class Logger {

    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static class LoggerLoader {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return LoggerLoader.INSTANCE;
    }

    private List<BiConsumer<LogLevel, String>> logListeners;

    private Logger() {
        logListeners = new ArrayList<>();
    }

    public void registerLogListener(BiConsumer<LogLevel, String> listener) {
        logListeners.add(listener);
    }

    public static void logInfo(String message) {
        getInstance().log(LogLevel.INFO, message);
    }

    public static void logWarning(String message) {
        getInstance().log(LogLevel.WARNING, message);
    }

    public static void logError(String message) {
        getInstance().log(LogLevel.ERROR, message);
    }

    public static void logFatal(String message) {
        getInstance().log(LogLevel.FATAL, message);
    }

    public void log(LogLevel level, String message) {
        System.out.println(String.format("[%s][%s] %s", dateFormat.format(new Date()), level.getName(), message));
        for(BiConsumer<LogLevel, String> listener : logListeners) listener.accept(level, message);
    }

}
