package example.managers;

import example.Main;
import example.auth.User;
import example.auth.UserRepository;
import example.myCommands.Command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRequestHandler.class);
    public Product handle(CommandRequest request, Map<String, Command> commandMap) {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.authenticateUser(request.getLogin(), request.getPassword());
        if (user == null) {
            LOGGER.info("User " + request.getLogin() + " is not authenticated");
            return new Product("Wrong login or password", false);
        }
        Command command = commandMap.get(request.getCommandType());
        LOGGER.info("Command " + request.getCommandType()+ " was received");
        Product product = command.execute(new CommandArgument(request.getArgument(), request.getLogin()));
        LOGGER.info("Command " + request.getCommandType()+ " was executed");
        return product;
    }
}