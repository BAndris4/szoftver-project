package boardgame.model;

/**
 * Represents a position on the game table with a specific row and column.
 *
 * @param row the row index of the position
 * @param column the column index of the position
 */
public record Position(int row, int column) {


    /**
     * Calculates the {@link Direction} representing the movement from one {@code Position} to another.
     * <p>
     * The method compares the row and column differences between {@code from} and {@code to} positions
     * and returns the corresponding {@code Direction} enum value.
     * If there is no change in position, {@link Direction#NONE} is returned.
     *
     * @param from the starting {@code Position}
     * @param to the target {@code Position}
     * @return the {@code Direction} indicating the movement from {@code from} to {@code to}
     */
    public static Direction getDirectionFromPositionChange(Position from, Position to) {
        if (from.column()-to.column() == 0){
            if (from.row()-to.row() == 0) {
                return Direction.NONE;
            } else if (from.row()-to.row() > 0){
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else if (from.column()-to.column() > 0) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    /**
     * Computes a new {@link Position} by moving from a starting {@code Position} in a given
     * {@link Direction} for a specified number of {@code steps}.
     * <p>
     * The new position is calculated by applying the direction's row and column offsets multiplied
     * by the number of steps to the starting position's coordinates.
     *
     * @param from the starting {@code Position}
     * @param direction the {@code Direction} to move towards
     * @param steps the number of steps to move in the given direction
     * @return the new {@code Position} reached after moving
     */
    public static Position getNewPosition(Position from, Direction direction, int steps) {
        int newRow = from.row() + direction.getRow() * steps;
        int newColumn = from.column() + direction.getColumn() * steps;
        return new Position(newRow, newColumn);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, column);
    }
}
