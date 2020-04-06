package io.github.techdweebgaming.modularjda.api.modules;

import io.github.techdweebgaming.modularjda.api.configs.ConfigurationManager;

import java.util.Optional;

public interface IModule {

    void initialize();

    Optional<ConfigurationManager> getConfigManager();

}
