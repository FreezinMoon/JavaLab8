package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;

import java.util.Comparator;

public class PrintFieldDescendingCategoryCommand extends Command {

    public PrintFieldDescendingCategoryCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "print_field_descending_category";
    }

    @Override
    public Product execute(CommandArgument argument) {
        String result = list.stream()
                .map(sm -> sm.getCategory().name())
                .sorted(Comparator.reverseOrder()).toList()
                .toString();
        return new Product(result, true);
    }
}