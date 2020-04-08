package io.github.techdweebgaming.modularjda.api.modules;

import io.github.techdweebgaming.modularjda.api.commands.CommandRegistryBase;
import io.github.techdweebgaming.modularjda.api.configs.ConfigurationManager;
import io.github.techdweebgaming.modularjda.api.configs.IConfig;
import io.github.techdweebgaming.modularjda.api.events.EventRegistry;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;
import io.github.techdweebgaming.modularjda.api.logger.LogLevel;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.internal.registries.CommandRegistryContainer;

import java.io.IOException;
import java.util.Optional;

public abstract class ModuleBase<T extends IConfig> implements IModule {

    protected final String name;
    private Optional<ConfigurationManager<T>> configManager;

    public ModuleBase(String name) {
        this.name = name;
        try {
            this.configManager = (Optional<ConfigurationManager<T>>) getConfigManager();
        } catch (ClassCastException e) {
            this.configManager = Optional.empty();
        }
    }

    public void initialize() throws DefaultNotFoundException, IOException, IllegalAccessException, NotInitializedException {
        logInfo("Initializing Module...");
        if(configManager.isPresent()) configManager.get().loadConfig();
        registerListeners(EventRegistry.getInstance());
        registerCommands(CommandRegistryContainer.getInstance().orElseThrow(() -> new NotInitializedException("Command Registry Not Initialized!")));
        logInfo("Module Initialized...");
    }

    protected abstract void registerListeners(EventRegistry registry);

    protected abstract void registerCommands(CommandRegistryBase commandRegistry);

    protected void log(LogLevel level, String message) {
        Logger.getInstance().log(level, String.format("[%s] %s", name, message));
    }

    private void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    protected Optional<T> getConfig() throws NotInitializedException {
        return configManager.isPresent() ? Optional.of(configManager.get().getConfig()) : Optional.empty();
    }

}
