package example.managers;


import example.objects.SpaceMarine;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class MyLinkedList implements Iterable<SpaceMarine> {

    private final LinkedList<SpaceMarine> list;

    private final java.time.ZonedDateTime creationDate;

    private final ReentrantLock lock = new ReentrantLock();

    public MyLinkedList() {
        creationDate = java.time.ZonedDateTime.now();
        list = new LinkedList<>();
    }

    public void add(SpaceMarine spaceMarine) {
        lock.lock();
        try {
            for (SpaceMarine marine : list) {
                if (marine.getId() == spaceMarine.getId()) {
                    spaceMarine.changeId(spaceMarine);
                    list.add(spaceMarine);
                    return;
                }
            }
            list.add(spaceMarine);
        } finally {
            lock.unlock();
        }
    }

    public String getCreationDate() {
        return "Initialization date: " + creationDate.toString();
    }

    public String getType() {
        return "Collection type: " + this.getClass().getName();
    }

    public void forEach(Consumer<? super SpaceMarine> action) {
        lock.lock();
        try {
            list.forEach(action);
        } finally {
            lock.unlock();
        }
    }

    public boolean removeIf(Predicate<? super SpaceMarine> filter) {
        lock.lock();
        try {
            return list.removeIf(filter);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return list.size();
        } finally {
            lock.unlock();
        }
    }

    public SpaceMarine get(int index) {
        lock.lock();
        try {
            return list.get(index);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            list.clear();
        } finally {
            lock.unlock();
        }
    }

    public void sort(Comparator<? super SpaceMarine> c) {
        lock.lock();
        try {
            list.sort(c);
        } finally {
            lock.unlock();
        }
    }

    public SpaceMarine remove(int index) {
        lock.lock();
        try {
            return list.remove(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<SpaceMarine> iterator() {
        lock.lock();
        try {
            return list.iterator();
        } finally {
            lock.unlock();
        }
    }

    public SpaceMarine getMax() {
        lock.lock();
        try {
            Comparator<? super SpaceMarine> comparator = new NameComparator();
            return Collections.max(list, comparator);
        } finally {
            lock.unlock();
        }
    }

    public SpaceMarine getMin() {
        lock.lock();
        try {
            Comparator<? super SpaceMarine> comparator = new NameComparator();
            return Collections.min(list, comparator);
        } finally {
            lock.unlock();
        }
    }

    public void set(int i, SpaceMarine newSpaceMarine) {
        lock.lock();
        try {
            list.set(i, newSpaceMarine);
        } finally {
            lock.unlock();
        }
    }

    public Stream<SpaceMarine> stream() {
        lock.lock();
        try {
            return list.stream();
        } finally {
            lock.unlock();
        }
    }

    public int indexOf(SpaceMarine sm) {
        lock.lock();
        try {
            return list.indexOf(sm);
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return list.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}