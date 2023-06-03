package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.ripManager.ChatStorage;

public class ChatCommand extends Command {
    public ChatCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {

        String result = ChatStorage.getExistingString() + commandArgument.argument();

        // Save the updated string in the ChatStorage
        ChatStorage.saveString(result);

        return new Product(result, true);
    }


    @Override
    public String getName() {
        return "chat";
    }
}
