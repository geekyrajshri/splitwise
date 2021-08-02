package com.splitwise.services.console.commands;

import java.util.List;

import static com.splitwise.services.console.ConsoleConfiguration.EXIT_COMMAND;

public class ExitCommand implements ICommand {
    @Override
    public boolean matches(List<String> input) {
        return input.size()>0 && input.get(0).toLowerCase().startsWith(EXIT_COMMAND);
    }

    @Override
    public void execute(List<String> input) {
        System.exit(0);
    }
}
