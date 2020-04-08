package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.logger.Logger;

import java.util.HashMap;
import java.util.Optional;

public class CommandArgsContainer {

    private HashMap<String, Optional<?>> args;

    public CommandArgsContainer (){
        args = new HashMap<>();
    }

    public void put(String key, Optional<?> value) {
        args.put(key.toLowerCase(), value);
    }

    public <T> T get(String key) {
        Optional<?> optional = args.getOrDefault(key.toLowerCase(), Optional.empty());
        if(!optional.isPresent()) return null;
        try {
            return (T) optional.get();
        } catch (ClassCastException e) {
            Logger.logError("Attempted to get command argument of wrong type!");
            return null;
        }
    }

}
