package io.github.techdweebgaming.modularjda.api.configs;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.conversion.Path;
import io.github.techdweebgaming.modularjda.api.exceptions.DefaultNotFoundException;
import io.github.techdweebgaming.modularjda.internal.types.Tuple;
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

    protected ConfigSpec getConfigSpec() throws IllegalAccessException, DefaultNotFoundException {
        ConfigSpec spec = new ConfigSpec();

        List<Tuple<String, Object>> defaults = new ArrayList<>();

        // Default Value Fields
        for(Field defaultValueField : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotation(ConfigSpecDefault.class))) {
            defaults.add(new Tuple<>(defaultValueField.getAnnotation(ConfigSpecDefault.class).value(), defaultValueField.get(null)));
        }

        // Standard fields
        for(Field field : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotations(Path.class, ConfigSpecEntry.class))) {
            Path pathAnnotation = field.getAnnotation(Path.class);
            ConfigSpecEntry entryAnnotation = field.getAnnotation(ConfigSpecEntry.class);

            Object defaultValue = entryAnnotation.defaultInClass() ? getDefaultFromPath(defaults, pathAnnotation.value()) : entryAnnotation.defaultValue();
            spec.define(pathAnnotation.value(), field.getType().cast(defaultValue));
        }

        // List Fields
        for(Field field : ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotations(Path.class, ConfigSpecEntryList.class))) {
            Path pathAnnotation = field.getAnnotation(Path.class);

            spec.defineList(pathAnnotation.value(), (List<?>) field.getType().cast(getDefaultFromPath(defaults, pathAnnotation.value())), p -> true);
        }
        return spec;
    }

    private Object getDefaultFromPath(List<Tuple<String, Object>> defaults, String path) throws DefaultNotFoundException {
        return defaults.stream()
                .filter((Tuple<String, Object> def) -> def.getFirst().equals(path))
                .findFirst().orElseThrow(() -> new DefaultNotFoundException()).getSecond();
    }

}
