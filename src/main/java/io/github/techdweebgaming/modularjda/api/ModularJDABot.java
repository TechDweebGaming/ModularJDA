package io.github.techdweebgaming.modularjda.api;

import io.github.techdweebgaming.modularjda.api.configs.ConfigurationManager;
import io.github.techdweebgaming.modularjda.api.configs.baseConfigs.CoreConfig;
import io.github.techdweebgaming.modularjda.api.events.EventListenerContainerConsumer;
import io.github.techdweebgaming.modularjda.api.events.EventRegistry;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.internal.registries.CommandRegistryContainer;
import io.github.techdweebgaming.modularjda.internal.registries.ModuleRegistry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class ModularJDABot {

    private static class ModularJDABotLoader {
        private static final ModularJDABot INSTANCE = new ModularJDABot();
    }

    public static ModularJDABot getInstance() {
        return ModularJDABotLoader.INSTANCE;
    }

    private JDABuilder builder;
    private ConfigurationManager<? extends CoreConfig> coreConfig;
    private Optional<JDA> bot;

    private ModularJDABot() {
        bot = Optional.empty();
    }

    public void setBot(JDABuilder builder) {
        this.builder = builder;
    }

    public void initialize(ModularJDASettings settings) throws IllegalAccessException, DefaultNotFoundException, NotInitializedException, InterruptedException, LoginException, IOException, InstantiationException {
        Logger.logInfo("Beginning ModularJDA Initialization...");
        Logger.logInfo("Loading Core Config...");
        coreConfig = new ConfigurationManager<>(settings.coreConfigClass, "core");
        coreConfig.loadConfig();
        Logger.logInfo("Core Config Loaded!");
        Logger.logInfo("Starting JDA Bot...");
        try {
            bot = Optional.of(
                    builder
                            .setToken(coreConfig.getConfig().token)
                            .build()
                            .awaitReady()
            );
        } catch (LoginException e) {
            Logger.logFatal("Failed to log into discord bot account!");
            throw e;
        }
        Logger.logInfo("JDA Bot Initialized!");
        Logger.logInfo("Preparing Registries...");
        // Prepare Command Registry
        CommandRegistryContainer.initialize(settings.commandRegistry);
        // Register the listener as a JDA listener
        bot.get().addEventListener(EventRegistry.getInstance());
        // Register the command registry with the multithreaded listener
        Consumer<MessageReceivedEvent> commandListenerConsumer = event -> CommandRegistryContainer.getInstance().get().messageReceived(event);
        EventRegistry.getInstance().registerListener(new EventListenerContainerConsumer(commandListenerConsumer, MessageReceivedEvent.class));
        Logger.logInfo("Registries Prepared!");
        Logger.logInfo("Initializing Modules...");
        ModuleRegistry.getInstance().initialize();
        Logger.logInfo("Modules Initialized!");
        Logger.logInfo("ModularJDA Initialization Complete!");
    }

    public JDA getBot() throws NotInitializedException {
        return bot.orElseThrow(() -> new NotInitializedException("Attempted to fetch bot object before it was constructed."));
    }

    public CoreConfig getConfig() throws NotInitializedException {
        return coreConfig.getConfig();
    }

}
