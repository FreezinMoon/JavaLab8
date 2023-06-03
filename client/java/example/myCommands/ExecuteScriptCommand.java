package example.myCommands;

import example.managers.Argument;
import example.managers.CommandExecutionException;
import example.managers.PreCommandRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Stack;

public class ExecuteScriptCommand extends Command {
    private final Stack<Path> scriptStack;
    private String arg;
    private String[] line;

    public ExecuteScriptCommand() {
        this.scriptStack = new Stack<>();
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public PreCommandRequest execute(Argument argument) throws CommandExecutionException {
        try {
            Path path = Path.of(argument.arg());
            if (scriptStack.contains(path)) {
                throw new CommandExecutionException("Script recursion detected");
            }
            scriptStack.push(path);


            Scanner scanner = new Scanner(path);
            while (scanner.hasNext()) {
                line = scanner.nextLine().split(" ");

                if (line.length > 1) {
                    arg = line[1];
                } else arg = "";
                argument.invoker().executeCommand(
                        line[0],
                        new Argument(
                                arg,
                                argument.invoker(),
                                argument.login(),
                                argument.password(),
                                argument.stage(),
                                null));
            }
            scriptStack.pop();
            return new PreCommandRequest(getName(), null);
        } catch (IOException e) {
            throw new CommandExecutionException("Error executing command: " + e.getMessage(), e);
        }
    }
}
