package io.github.techdweebgaming.modularjda.api.commands;

import io.github.techdweebgaming.modularjda.api.ModularJDABot;
import io.github.techdweebgaming.modularjda.api.exceptions.NotInitializedException;
import io.github.techdweebgaming.modularjda.api.logger.Logger;
import io.github.techdweebgaming.modularjda.api.types.Tuple;

import java.util.List;

public abstract class CommandBase implements ICommand {

    @Override
    public String getHelp() {
        try {
            StringBuilder help = new StringBuilder();
            help.append(ModularJDABot.getInstance().getConfig().prefix);
            help.append(getAliases().get(0));
            Tuple<List<CommandFlag>, List<CommandArgument>> commandParams = getArguments();
            for(CommandFlag flag : commandParams.getFirst()) {
                help.append(flag.optional ? " [-" : " -");
                help.append(flag.getFlag().toLowerCase());
                if(flag.hasValue()) help.append(" (value)");
                help.append(flag.optional ? "]" : "");
            }
            for(CommandArgument arg : commandParams.getSecond()) {
                help.append(arg.optional ? " [" : " (");
                help.append(arg.name.toLowerCase());
                help.append(arg.optional ? "]" : ")");
            }
            return help.toString();
        } catch (NotInitializedException e) {
            Logger.logError("An error has occurred attempting to generate command help!");
            return "An internal error has occurred. Please contact staff for assistance.";
        }
    }

}
