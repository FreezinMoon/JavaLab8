package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

public abstract class Command {
    protected final MyLinkedList list;

    protected Command(MyLinkedList list) {
        this.list = list;
    }

    public abstract Product execute(CommandArgument commandArgument);

    public abstract String getName();
}
