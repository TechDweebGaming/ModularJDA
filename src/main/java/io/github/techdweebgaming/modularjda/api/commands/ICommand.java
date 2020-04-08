package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.types.Tuple;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void execute(CommandArgsContainer args, MessageReceivedEvent event);

    boolean hasPermission(Member member, MessageReceivedEvent event);

    boolean validChannel(TextChannel channel, MessageReceivedEvent event);

    List<String> getAliases();

    Tuple<List<CommandFlag>, List<CommandArgument>> getArguments();

}
