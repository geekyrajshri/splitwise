package com.splitwise.services.console.handlers;

import com.splitwise.services.console.commands.ICommand;
import com.splitwise.services.console.exceptions.UnsupportedCommandException;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements ICommandHandler {
    List<ICommand> commands = new ArrayList<>();
    @Override
    public void registerCommand(ICommand iCommand) {
        commands.add(iCommand);
    }

    @Override
    public boolean matches(List<String> input) {
        return commands.stream().anyMatch(c->c.matches(input));
    }

    @Override
    public void execute(List<String> input) {
            getCommand(input).execute(input);
    }

    @Override
    public ICommand getCommand(List<String> input) {
        for (ICommand command:commands){
            if(command.matches(input)){
                return command;
            }
        }
        throw new UnsupportedCommandException(input.toString());
    }


}
