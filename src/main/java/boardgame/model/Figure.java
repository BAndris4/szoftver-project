package boardgame.model;

/**
 * Represents a figure on the game board, holding its current {@link Position}
 * and the {@link Direction} of its last movement.
 * The figure is immutable and can be constructed with position and last move direction.
 */
public record Figure(Position position, Direction lastMove) {

    /**
     * Default constructor creating a figure at position (0,0) with Direction.NONE as last move.
     */
    public Figure() {
        this(new Position(0, 0), Direction.NONE);
    }

}
