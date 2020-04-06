package io.github.techdweebgaming.modularjda.api.logger;

public enum LogLevel {

    INFO(1, "INFO"),
    WARNING(2, "WARNING"),
    ERROR(3, "ERROR"),
    FATAL(4, "FATAL");

    private final int level;
    private final String name;

    LogLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

}
