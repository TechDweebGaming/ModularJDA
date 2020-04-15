package io.github.techdweebgaming.modularjda.api.commands.argImpls;

import io.github.techdweebgaming.modularjda.api.commands.CommandFlag;
import io.github.techdweebgaming.modularjda.api.commands.converters.BooleanConverter;

public class ValuelessCommandFlag extends CommandFlag<Boolean> {

    public ValuelessCommandFlag(String flag, String name, String description) {
        super(flag, false, name, description, -1, -1, true, new BooleanConverter());
    }

}
