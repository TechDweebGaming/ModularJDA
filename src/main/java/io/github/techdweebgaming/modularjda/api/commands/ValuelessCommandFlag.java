package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.commands.converters.BooleanConverter;

public class ValuelessCommandFlag extends CommandFlag<Boolean> {

    public ValuelessCommandFlag(String flag, String name, String description, boolean optional) {
        super(flag, false, name, description, -1, -1, optional, new BooleanConverter());
    }

}
