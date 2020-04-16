package io.github.techdweebgaming.modularjda.api.commands.argImpls;

import io.github.techdweebgaming.modularjda.api.commands.CommandArgument;
import io.github.techdweebgaming.modularjda.api.commands.converters.StringConverter;
import io.github.techdweebgaming.modularjda.api.types.Tuple;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;

import java.util.Optional;
import java.util.Queue;

public class CommandArgumentUser extends CommandArgument<String> {

    public CommandArgumentUser(String name, String description, boolean optional) {
        super(name, description, -1, -1, optional, new StringConverter());
    }

    @Override
    public Tuple<Optional<String>, Queue<String>> consumeCommand(Queue<String> command) throws ArgumentNotPresentException {
        String argument = command.peek();
        if(!(argument == null || !argument.matches("<@!?\\d+>"))) {
            return new Tuple(Optional.of(command.poll().replaceAll("[<@!>]", "")), command);
        }

        if(optional) return new Tuple(Optional.empty(), command);
        else throw new ArgumentNotPresentException();
    }

}
