package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.commands.converters.IConverter;
import io.github.techdweebgaming.modularjda.api.commands.converters.InvalidConversionException;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;
import io.github.techdweebgaming.modularjda.api.types.Tuple;

import java.util.Optional;
import java.util.Queue;

public class CommandArgument<T> extends CommandElementBase<T, Queue<String>> {

    public CommandArgument(String name, String description, int minLength, int maxLength, boolean optional, IConverter<T> converter) {
        super(name, description, minLength, maxLength, optional, converter);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Tuple<Optional<T>, Queue<String>> consumeCommand(Queue<String> command) throws ArgumentNotPresentException {
        String argument = command.peek();
        boolean valid = argument != null && argument.length() >= minLength;
        if(valid && maxLength >= 0) valid = argument.length() <= maxLength;

        if(valid) {
            try {
                return new Tuple<>(Optional.of(converter.fromString(command.poll())), command);
            } catch (InvalidConversionException e) { }
        }

        if(optional) return new Tuple<>(Optional.empty(), command);
        else throw new ArgumentNotPresentException();
    }

    public static class CommandArgumentBuilder<T> extends CommandElementBuilderBase<CommandArgument<T>, CommandArgument.CommandArgumentBuilder, T> {

        public CommandArgumentBuilder(String name, String description, IConverter<T> converter) {
            super(name, description, converter);
        }

        public CommandArgument<T> build() {
            return new CommandArgument<>(name, description, minLength, maxLength, optional, converter);
        }
    }

}
