package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

public class AuthenticationCommand extends Command {

    public AuthenticationCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        return new Product("Authentication successful", true);
    }

    @Override
    public String getName() {
        return "authentication";
    }
}
