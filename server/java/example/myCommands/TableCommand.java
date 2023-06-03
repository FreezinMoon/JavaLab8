package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.objects.SpaceMarine;

import java.util.Comparator;
import java.util.stream.Collectors;

public class TableCommand extends Command {

    public TableCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public Product execute(CommandArgument commandArgument) {
        String result = list.stream()
                .map(SpaceMarine::getStringToCSV)
                .collect(Collectors.joining("\n"));
        return new Product(result, true);
    }

    @Override
    public String getName() {
        return "table";
    }
}
