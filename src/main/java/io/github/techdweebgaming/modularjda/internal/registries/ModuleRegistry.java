package io.github.techdweebgaming.modularjda.internal.registries;

import io.github.techdweebgaming.modularjda.api.modules.ModuleClass;
import io.github.techdweebgaming.modularjda.api.modules.IModule;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.internal.services.MultithreadedTasksService;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ModuleRegistry {

    private static class ModuleRegistryLoader {
        private static final ModuleRegistry INSTANCE = new ModuleRegistry();
    }

    public static ModuleRegistry getInstance() {
        return ModuleRegistryLoader.INSTANCE;
    }

    private List<IModule> modules;

    private ModuleRegistry() {
        modules = new ArrayList<>();
    }

    public void initialize() {
        // Discover all modules
        Reflections reflections = new Reflections();
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ModuleClass.class);
        for (Class<?> moduleClass : annotatedClasses) {
            try {
                IModule module = (IModule) moduleClass.newInstance();
                modules.add(module);
            } catch (IllegalAccessException | InstantiationException e) {
                Logger.logError(String.format("Could not instantiate module %s. Does it have a public constructor with no parameters?", moduleClass.getCanonicalName()));
            } catch (ClassCastException e) {
                Logger.logError("ModuleClass annotation present on non-module class. Skipping module.");
            }
        }

        // Instantiate all modules
        try(MultithreadedTasksService<IModule> moduleInstantiator = new MultithreadedTasksService<>(3, (IModule module) -> module.initialize(), modules)) {
            while(!moduleInstantiator.complete());
        }
    }

    public <T extends IModule> Optional<T> getModule(Class<T> clazz) {
        return modules.stream()
                .filter(clazz::isInstance)
                .findFirst()
                .map(clazz::cast);
    }
}
