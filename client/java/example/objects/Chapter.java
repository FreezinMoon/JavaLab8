package example.objects;

import java.io.Serializable;

public record Chapter(Long marinesCount, String name) implements Serializable {

    @Override
    public String toString() {
        return "Chapter{" + "name='" + name + '\'' + ", marinesCount=" + marinesCount + '}';
    }
}