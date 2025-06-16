package boardgame.model;

/**
 * Represents a position on the game table with a specific row and column.
 */
public record Position(int row, int column) {

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, column);
    }
}
