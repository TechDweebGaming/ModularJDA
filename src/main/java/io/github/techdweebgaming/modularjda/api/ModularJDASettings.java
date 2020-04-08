package io.github.techdweebgaming.modularjda.api;

import io.github.techdweebgaming.modularjda.api.commands.CommandRegistryBase;
import io.github.techdweebgaming.modularjda.api.configs.baseConfigs.CoreConfig;

public class ModularJDASettings {

    public final CommandRegistryBase commandRegistry;
    public final Class<? extends CoreConfig> coreConfigClass;

    private ModularJDASettings(CommandRegistryBase commandRegistry, Class<? extends CoreConfig> coreConfigClass) {
        this.commandRegistry = commandRegistry;
        this.coreConfigClass = coreConfigClass;
    }

    public static class ModularJDASettingsBuilder {

        private CommandRegistryBase commandRegistry;
        private Class<? extends CoreConfig> coreConfigClass;

        public ModularJDASettingsBuilder(CommandRegistryBase commandRegistry) {
            this.commandRegistry = commandRegistry;
            coreConfigClass = CoreConfig.class;
        }

        public ModularJDASettingsBuilder setCoreConfigClass(Class<? extends CoreConfig> clazz) {
            coreConfigClass = clazz;
            return this;
        }

        public ModularJDASettings build() {
            return new ModularJDASettings(commandRegistry, coreConfigClass);
        }

    }
}
