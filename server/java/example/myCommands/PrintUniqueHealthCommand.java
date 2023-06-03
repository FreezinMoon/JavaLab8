package example.myCommands;

import example.managers.CommandArgument;
import example.managers.MyLinkedList;
import example.managers.Product;
import example.objects.SpaceMarine;

import java.util.stream.Collectors;

public class PrintUniqueHealthCommand extends Command {

    public PrintUniqueHealthCommand(MyLinkedList list) {
        super(list);
    }

    @Override
    public String getName() {
        return "print_unique_health";
    }

    @Override
    public Product execute(CommandArgument argument) {
        String result = list.stream()
                .map(SpaceMarine::getHealth)
                .distinct()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return new Product(result, true);
    }
}