package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.ModularJDABot;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;
import io.github.techdweebgaming.modularjda.api.exceptions.commands.InvalidArgumentsException;
import io.github.techdweebgaming.modularjda.api.exceptions.commands.InvalidChannelException;
import io.github.techdweebgaming.modularjda.api.exceptions.commands.NoPermissionsException;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.api.types.Tuple;
import io.github.techdweebgaming.modularjda.internal.exceptions.ArgumentNotPresentException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public abstract class CommandRegistryBase {

    private List<ICommand> commands;

    public CommandRegistryBase() {
        commands = new ArrayList<>();
    }

    public void registerCommand(ICommand command) {
        commands.add(command);
    }

    public void registerCommands(ICommand... commands) {
        for(ICommand command : commands) registerCommand(command);
    }

    public List<ICommand> getAllCommands() {
        return commands;
    }

    public Optional<ICommand> getCommand(String key) {
        return commands.stream()
                .filter(command -> command.getAliases().contains(key.toLowerCase()))
                .findFirst();
    }

    public void messageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        try {
            List<String> splitMessage = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
            if(!splitMessage.get(0).startsWith(ModularJDABot.getInstance().getConfig().prefix)) return;
            final String processedCommandString = splitMessage.get(0).substring(1);
            Optional<ICommand> commandOptional = getCommand(processedCommandString);
            if(!commandOptional.isPresent()) return;
            ICommand command = commandOptional.get();
            try {
                splitMessage.remove(0);
                /// <-- From now on, we know what command we have. We just need to process it -->
                if (!command.hasPermission(event.getMember(), event)) throw new NoPermissionsException();
                if(!command.validChannel(event.getTextChannel(), event)) throw new InvalidChannelException();

                Tuple<List<CommandFlag>, List<CommandArgument>> commandArgs = command.getArguments();
                CommandArgsContainer args = new CommandArgsContainer();
                try {
                    for(CommandFlag flag : commandArgs.getFirst()) {
                        Tuple<Optional<?>, List<String>> flagOutput = flag.consumeCommand(splitMessage);
                        splitMessage = flagOutput.getSecond();
                        args.put(flag.getName().toLowerCase(), flagOutput.getFirst());
                    }
                    Queue<String> splitMessageQueue = new LinkedList<>(splitMessage);
                    for(CommandArgument argument : commandArgs.getSecond()) {
                        Tuple<Optional<?>, Queue<String>> argOutput = argument.consumeCommand(splitMessageQueue);
                        splitMessageQueue = argOutput.getSecond();
                        args.put(argument.getName().toLowerCase(), argOutput.getFirst());
                    }
                } catch (ArgumentNotPresentException e) {
                    throw new InvalidArgumentsException();
                }
                command.execute(args, event);
            } catch (NoPermissionsException e) {
                noPermissions(event, command);
                return;
            } catch (InvalidChannelException e) {
                invalidChannel(event, command);
                return;
            } catch (InvalidArgumentsException e) {
                invalidArgs(event, command);
                return;
            }
        } catch (NotInitializedException e) {
            Logger.logError("Ignoring command due to core config not being initialized!");
        }
    }

    protected abstract void noPermissions(MessageReceivedEvent event, ICommand command);
    protected abstract void invalidChannel(MessageReceivedEvent event, ICommand command);
    protected abstract void invalidArgs(MessageReceivedEvent event, ICommand command);

}
