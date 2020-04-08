package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.commands.converters.IConverter;
import io.github.techdweebgaming.modularjda.api.commands.converters.InvalidConversionException;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;
import io.github.techdweebgaming.modularjda.api.types.Tuple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandFlag<T> extends CommandElementBase<T, List<String>> {

    private String flag;
    private boolean hasValue;

    public CommandFlag(String flag, boolean hasValue, String name, String description, int minLength, int maxLength, boolean optional, IConverter<T> converter) {
        super(name, description, minLength, maxLength, optional, converter);
        this.flag = flag;
        this.hasValue = hasValue;
    }

    @Override
    public Tuple<Optional<T>, List<String>> consumeCommand(List<String> command) throws ArgumentNotPresentException {
        List<String> caseInsensitiveCommand = command.stream().map(String::toLowerCase).collect(Collectors.toList());

        boolean valid = caseInsensitiveCommand.contains("-" + flag.toLowerCase());
        if(hasValue && valid) {
            int valueIndex = caseInsensitiveCommand.indexOf("-" + flag.toLowerCase()) + 1;
            if (command.size() <= valueIndex) throw new ArgumentNotPresentException();
            String value = command.get(valueIndex);

            valid = value.length() >= minLength;
            if(valid && maxLength >= 0) valid = value.length() <= maxLength;

            if(valid) {
                try {
                    Tuple<Optional<T>, List<String>> output = new Tuple<>(Optional.of(converter.fromString(value)), command);
                    command.remove(valueIndex);
                    command.remove(valueIndex - 1);
                    return output;
                } catch (InvalidConversionException e) {}
            }
            throw new ArgumentNotPresentException();
        } else {
            if(valid) {
                command.remove(caseInsensitiveCommand.indexOf("-" + flag.toLowerCase()));
                return new Tuple<>((Optional<T>) Optional.of(true), command);
            }
            if(optional) return new Tuple<>(Optional.empty(), command);
            throw new ArgumentNotPresentException();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public static class CommandFlagBuilder<T> extends CommandElementBuilderBase<CommandFlag<T>, CommandFlag.CommandFlagBuilder, T> {
        private String flag;
        private boolean hasValue;

        public CommandFlagBuilder(String flag, boolean hasValue, String name, String description, IConverter<T> converter) {
            super(name, description, converter);
            this.flag = flag;
            this.hasValue = hasValue;
            optional = true;
        }

        public CommandFlag<T> build() {
            return new CommandFlag<>(flag, hasValue, name, description, minLength, maxLength, optional, converter);
        }
    }

}
