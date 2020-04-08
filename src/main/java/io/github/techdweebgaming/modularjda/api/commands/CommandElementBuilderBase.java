package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.commands.converters.IConverter;

public abstract class CommandElementBuilderBase<T extends CommandElementBase, U extends CommandElementBuilderBase, V> {

    protected String name;
    protected String description;
    protected int minLength;
    protected int maxLength;
    protected boolean optional;

    protected IConverter<V> converter;

    public CommandElementBuilderBase(String name, String description, IConverter<V> converter) {
        this.name = name;
        this.description = description;
        this.converter = converter;
    }

    public U setOptional(boolean optional) {
        this.optional = optional;
        return (U) this;
    }

    public U setLengthRequirements(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        return (U) this;
    }

    public abstract T build();

}
