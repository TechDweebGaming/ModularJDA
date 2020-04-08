package io.github.techdweebgaming.modularjda.api.commands.converters;

public class BooleanConverter implements IConverter<Boolean> {
    @Override
    public Boolean fromString(String string) {
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("t") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("y");
    }
}
