/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
public class Position {
    public int col;
    public int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position that = (Position) o;
        return col == that.col && row == that.row;
    }

    @Override
    public int hashCode() {
        return 31 * col + row;
    }

    @Override
    public String toString() {
        return "(" + col + ", " + row + ")";
    }
}


