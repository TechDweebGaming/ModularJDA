package io.github.techdweebgaming.modularjda.api.commands.converters;

public interface IConverter<T> {

    T fromString(String string) throws InvalidConversionException;

}
