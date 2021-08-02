package com.splitwise.services.console.commands;

import com.splitwise.Splitwise;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.authentication.ConsoleAuthenticationContext;
import com.splitwise.services.console.exceptions.UnsupportedCommandException;
import com.splitwise.services.console.handlers.CommandHandler;

import java.util.List;

public class AuthenticatedCommandHandler extends CommandHandler implements ICommand {
    AuthenticationContext authenticationContext;
    public AuthenticatedCommandHandler(Splitwise splitwise) {
        authenticationContext = new ConsoleAuthenticationContext(splitwise.getUserRepository());
    }

    @Override
    public boolean matches(List<String> input) {
        try {
            String firstInput = input.get(0).toLowerCase();
            if (firstInput.charAt(0) != 'u') {
                return false;
            }
            Long userId = Long.parseLong(firstInput.substring(1));
        }catch (NumberFormatException e){
            return false;
        }
        return super.matches(input.subList(1,input.size()));
    }

    @Override
    public void execute(List<String> input) {
        String firstInput = input.get(0).toLowerCase();
        Long userId = Long.parseLong(firstInput.substring(1));
        authenticationContext.setUserId(userId);

        ICommand command = getCommand(input.subList(1,input.size()));
        if(command instanceof IAuthenticatedCommand){
            ((IAuthenticatedCommand)command).setAuthenticationContext(authenticationContext);
        }
        command.execute(input.subList(1,input.size()));
    }

}
