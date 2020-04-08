package io.github.techdweebgaming.modularjda.api.configs;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.conversion.Path;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.api.types.Tuple;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager<T extends IConfig> extends ConfigurationManagerBase<T>{

    Class<T> clazz;

    public ConfigurationManager(Class<T> clazz, String name) {
        super(clazz, name);
        this.clazz = clazz;
    }

    protected ConfigSpec getConfigSpec() throws IllegalAccessException, DefaultNotFoundException, InstantiationException {
        ConfigSpec spec = new ConfigSpec();

        T instance = clazz.newInstance();

        // Standard fields
        for(Field field : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotations(Path.class, ConfigSpecEntry.class))) {
            Path pathAnnotation = field.getAnnotation(Path.class);

            spec.define(pathAnnotation.value(), field.get(instance));
        }

        // List Fields
        for(Field field : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotations(Path.class, ConfigSpecEntryList.class))) {
            Path pathAnnotation = field.getAnnotation(Path.class);

            spec.defineList(pathAnnotation.value(), (List<?>) field.get(instance), p -> true);
        }
        return spec;
    }

}
