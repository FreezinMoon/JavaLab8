package example.managers;

import java.io.Serializable;

public class CommandRequest implements Serializable {
    private final String commandName;
    private final Object argument;
    private final String login;
    private final String password;

    public CommandRequest(String commandName, Object argument, String login, String password) {
        this.commandName = commandName;
        this.argument = argument;
        this.login = login;
        this.password = password;
    }

    public String getCommandType() {
        return commandName;
    }

    public Object getArgument() {
        return argument;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}