package io.github.techdweebgaming.modularjda.api.commands.argImpls;

import io.github.techdweebgaming.modularjda.api.commands.CommandArgument;
import io.github.techdweebgaming.modularjda.api.commands.converters.StringConverter;
import io.github.techdweebgaming.modularjda.api.types.Tuple;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class CommandArgumentMulti extends CommandArgument<String> {

    public CommandArgumentMulti(String name, String description, boolean optional) {
        super(name, description, -1, -1, optional, new StringConverter());
    }

    @Override
    public Tuple<Optional<String>, Queue<String>> consumeCommand(Queue<String> command) throws ArgumentNotPresentException {
        if(command.peek() != null) return new Tuple<>(Optional.of(command.stream().collect(Collectors.joining(" "))), new LinkedList<>());

        if(optional) return new Tuple<>(Optional.empty(), command);
        else throw new ArgumentNotPresentException();
    }
}
