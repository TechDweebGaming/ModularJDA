package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.commands.converters.IConverter;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;
import io.github.techdweebgaming.modularjda.api.types.Tuple;

import java.util.Collection;
import java.util.Optional;

public abstract class CommandElementBase<T, S extends Collection<String>> {

    protected String name;
    protected String description;
    protected int minLength;
    protected int maxLength;
    protected boolean optional;

    protected IConverter<T> converter;

    public CommandElementBase(String name, String description, int minLength, int maxLength, boolean optional, IConverter<T> converter) {
        this.name = name;
        this.description = description;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.optional = optional;
        this.converter = converter;
    }

    public abstract Tuple<Optional<T>, S> consumeCommand(S command) throws ArgumentNotPresentException;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Boolean getOptional() {
        return optional;
    }

}
