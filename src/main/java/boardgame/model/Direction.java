package boardgame.model;

public enum Direction {
    UP(-1,0),
    RIGHT(0,1),
    LEFT(0, -1),
    DOWN(1, 0),
    NONE(0,0);

    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
