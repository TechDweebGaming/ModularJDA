package io.github.techdweebgaming.modularjda.internal.registries;

import io.github.techdweebgaming.modularjda.api.commands.CommandRegistryBase;

import java.util.Optional;

public class CommandRegistryContainer {

    protected static Optional<CommandRegistryBase> commandRegistry = Optional.empty();

    public static Optional<CommandRegistryBase> getInstance() {
        return commandRegistry;
    }

    public static void initialize(CommandRegistryBase commandRegistry) {
        CommandRegistryContainer.commandRegistry = Optional.of(commandRegistry);
    }

}
