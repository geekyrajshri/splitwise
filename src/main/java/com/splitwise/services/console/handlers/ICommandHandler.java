package com.splitwise.services.console.handlers;

import com.splitwise.services.console.commands.ICommand;

import java.util.List;

public interface ICommandHandler {

    void registerCommand(ICommand iCommand);

    boolean matches(List<String> input);

    void execute(List<String> input);

    ICommand getCommand(List<String> input);
}
