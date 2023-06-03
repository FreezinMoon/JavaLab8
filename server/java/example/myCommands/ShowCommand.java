package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.NameComparator;
import example.managers.Product;
import example.objects.SpaceMarine;

import java.util.stream.Collectors;

public class ShowCommand extends Command {
    NameComparator comparator;

    public ShowCommand(MyLinkedList list) {
        super(list);
        this.comparator = new NameComparator();
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public Product execute(CommandArgument argument) {
        String result = list.stream()
                .sorted(comparator)
                .map(SpaceMarine::toString)
                .collect(Collectors.joining("\n"));
        return new Product(result + '\n', true);
    }
}