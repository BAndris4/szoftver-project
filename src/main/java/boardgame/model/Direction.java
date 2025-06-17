package boardgame.model;

import java.util.List;

/**
 * Enum representing directions with corresponding row and column offsets.
 * Each direction specifies how the row and column indices change when moving in that direction.
 */
public enum Direction {
    UP(-1,0),
    RIGHT(0,1),
    LEFT(0, -1),
    DOWN(1, 0),
    NONE(0,0);

    private final int row;
    private final int column;

    /**
     * Constructs a Direction with specified row and column offset.
     * @param row the row offset for this direction
     * @param column the column offset for this direction
     */
    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row offset indicating the vertical movement of this direction.
     * @return the row offset of this direction
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column offset indicating the horizontal movement of this direction.
     * @return the column offset of this direction
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns a list of all directions that represent actual movement on the board,
     * excluding the {@link #NONE} direction.
     * <p>
     * These directions are: {@link #UP}, {@link #DOWN}, {@link #LEFT}, and {@link #RIGHT}.
     *
     * @return a list of movement-related directions
     */
    public static List<Direction> getMovableDirections() {
        return List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    }
}
