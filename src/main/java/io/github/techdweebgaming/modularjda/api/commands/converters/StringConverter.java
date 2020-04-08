package io.github.techdweebgaming.modularjda.api.commands.converters;

public class StringConverter implements IConverter<String> {
    @Override
    public String fromString(String string) {
        return string;
    }
}
