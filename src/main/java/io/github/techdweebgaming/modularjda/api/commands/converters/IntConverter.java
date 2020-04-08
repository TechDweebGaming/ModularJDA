package io.github.techdweebgaming.modularjda.api.commands.converters;

public class IntConverter implements IConverter<Integer> {
    @Override
    public Integer fromString(String string) throws InvalidConversionException {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new InvalidConversionException();
        }
    }
}
