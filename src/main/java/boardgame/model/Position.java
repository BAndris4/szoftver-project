package boardgame.model;

/**
 * Represents a position on the game table with a specific row and column.
 */
public record Position(int row, int column) {

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, column);
    }

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

    public static Position getNewPosition(Position from, Direction direction, int steps) {
        int newRow = from.row() + direction.getRow() * steps;
        int newColumn = from.column() + direction.getColumn() * steps;
        return new Position(newRow, newColumn);
    }
}
