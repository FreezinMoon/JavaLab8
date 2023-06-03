package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        return new Product("Script has been successfully executed", true);
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
