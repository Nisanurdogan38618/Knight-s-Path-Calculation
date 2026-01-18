/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.util.*;

public class Tile {
    public int column, row, type;
    public List<Tile> adjacentTiles;

    public Tile(int col, int row, int type) {
        this.column = col;
        this.row = row;
        this.type = type;
        this.adjacentTiles = new ArrayList<>();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile t = (Tile) o;
        return column == t.column && row == t.row;
    }

    public int hashCode() {
        return Objects.hash(column, row);
    }

    public String toString() {
        return "(" + column + ", " + row + ")";
    }
}

