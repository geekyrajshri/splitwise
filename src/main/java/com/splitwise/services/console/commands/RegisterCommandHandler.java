package com.splitwise.services.console.commands;

import com.splitwise.Splitwise;
import com.splitwise.controllers.UserController;
import com.splitwise.dtos.UserDTO;
import com.splitwise.models.User;

import java.util.List;

import static com.splitwise.services.console.ConsoleConfiguration.REGISTER_COMMAND;

public class RegisterCommandHandler implements ICommand {
    UserController userController;

    public RegisterCommandHandler(Splitwise splitwise){
        this.userController = splitwise.getUserController();
    }

    @Override
    public boolean matches(List<String> input) {
        return input.size() >= 4 && input.get(0).toLowerCase().startsWith(REGISTER_COMMAND);
    }

    @Override
    public void execute(List<String> input) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = input.get(1);
        userDTO.phoneNumber = input.get(2);
        userDTO.password = input.get(3);
        User user = userController.register(userDTO);
        System.out.println(user.toString());
    }
}
