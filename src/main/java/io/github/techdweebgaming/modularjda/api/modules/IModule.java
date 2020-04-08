package io.github.techdweebgaming.modularjda.api.modules;

import io.github.techdweebgaming.modularjda.api.configs.ConfigurationManager;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;

import java.io.IOException;
import java.util.Optional;

public interface IModule {

    void initialize() throws DefaultNotFoundException, IOException, IllegalAccessException, NotInitializedException;

    Optional<? extends ConfigurationManager> getConfigManager();

}
