package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.exceptions.commands.InvalidArgumentsException;
import io.github.techdweebgaming.modularjda.api.exceptions.commands.InvalidChannelException;
import io.github.techdweebgaming.modularjda.api.exceptions.commands.NoPermissionsException;
import io.github.techdweebgaming.modularjda.api.types.Tuple;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {

    String getName();
    String getDescription();
    String getHelp();

    void execute(CommandArgsContainer args, MessageReceivedEvent event) throws InvalidArgumentsException, InvalidChannelException, NoPermissionsException;

    boolean hasPermission(Member member, MessageReceivedEvent event);

    boolean validChannel(TextChannel channel, MessageReceivedEvent event);

    List<String> getAliases();

    Tuple<List<CommandFlag>, List<CommandArgument>> getArguments();

}
