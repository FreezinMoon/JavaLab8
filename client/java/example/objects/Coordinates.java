package example.objects;

import java.io.Serializable;

public record Coordinates(double x, Float y) implements Serializable {

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}