package example.managers;

import example.objects.SpaceMarine;

import java.util.Comparator;

public class NameComparator implements Comparator<SpaceMarine> {
    @Override
    public int compare(SpaceMarine sm1, SpaceMarine sm2) {
        return CharSequence.compare(sm1.getName(), sm2.getName());
    }
}
