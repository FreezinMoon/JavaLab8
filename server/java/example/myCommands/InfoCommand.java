package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

public class InfoCommand extends Command {


    public InfoCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public Product execute(CommandArgument argument) {
        String s = "Collection info:\n" +
                list.getType() + "\n" +
                list.getCreationDate() + "\n" +
                "Size: " + list.size() + "\n";
        return new Product(s, true);
    }
}
