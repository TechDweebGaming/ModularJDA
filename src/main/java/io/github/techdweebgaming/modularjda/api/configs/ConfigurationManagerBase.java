package io.github.techdweebgaming.modularjda.api.configs;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.file.FileConfig;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;
import io.github.techdweebgaming.modularjda.api.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class ConfigurationManagerBase<T extends IConfig> {

    private Optional<T> config;
    private Class<T> clazz;
    private String name;

    protected ConfigurationManagerBase(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
        config = Optional.empty();
    }

    protected abstract ConfigSpec getConfigSpec() throws IllegalAccessException, DefaultNotFoundException;

    public T getConfig() throws NotInitializedException {
        return config.orElseThrow(() -> new NotInitializedException(String.format("Attempted to access the config \"%s\" without first loading it!", name)));
    }

    public void loadConfig() throws IllegalAccessException, DefaultNotFoundException, IOException {
        File configFolder = new File("configs");
        if(!configFolder.exists()) configFolder.mkdirs();
        FileConfig configFile = FileConfig.of("configs" + File.separator + name + ".toml");
        if(!configFile.getFile().exists()) configFile.getFile().createNewFile();
        configFile.load();
        try {
            ConfigSpec configSpec = getConfigSpec();
            if(!configSpec.isCorrect(configFile)) {
                configSpec.correct(configFile);
                configFile.save();
            }
        } catch (IllegalAccessException e) {
            Logger.logFatal(String.format("Config \"%s\" contains a non-public field. Please ensure all config fields are public!", name));
            throw e;
        } catch (DefaultNotFoundException e) {
            Logger.logFatal(String.format("Config \"%s\" does not contain appropriate default value fields!"));
            throw e;
        }

        config = Optional.of(new ObjectConverter().toObject(configFile, getSupplier()));
        configFile.close();
    }

    private Supplier<T> getSupplier() {
        return () -> {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Logger.logFatal(String.format("Could not instantiate config object representation for config \"%s\". Does it have a public constructor with no parameters?", name));
                return null;
            }
        };
    }
}
