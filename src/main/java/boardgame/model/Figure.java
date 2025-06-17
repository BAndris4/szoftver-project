package boardgame.model;

import java.util.Objects;

/**
 * Represents a figure on the game board, holding its current {@link Position}
 * and the {@link Direction} of its last movement.
 * The figure can be moved by updating its position and last move direction.
 */
public class Figure {

    private Position position;
    private Direction lastMove;

    /**
     * Creates a new figure at the default starting position (0,0)
     * with no movement direction.
     */
    public Figure() {
        this.position = new Position(0, 0);
        this.lastMove = Direction.NONE;
    }

    /**
     * Creates a new figure with the specified position and last movement direction.
     *
     * @param position the initial position of the figure
     * @param lastMove the last move direction of the figure
     */
    public Figure(Position position, Direction lastMove) {
        this.position = position;
        this.lastMove = lastMove;
    }

    /**
     * Returns the current position of the figure.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the direction of the last move the figure made.
     *
     * @return the last move direction
     */
    public Direction getLastMove() {
        return lastMove;
    }

    /**
     * Sets the figure's position to the given value.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Sets the direction of the figure's last move.
     *
     * @param lastMove the direction of the last move
     */
    public void setLastMove(Direction lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Indicates whether some other object is equal to this one.
     * Two figures are considered equal if they have the same position
     * and last move direction.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return Objects.equals(position, figure.position) && lastMove == figure.lastMove;
    }

    /**
     * Returns a hash code value for the figure.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns a string representation of the figure.
     *
     * @return a string describing the figure
     */
    @Override
    public String toString() {
        return String.format("Figure{position=%s, lastMove=%s}", position, lastMove);
    }
}
