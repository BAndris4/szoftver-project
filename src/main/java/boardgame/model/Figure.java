package boardgame.model;

import java.util.Objects;

public class Figure {

    private Position position;
    private Direction lastMove;

    public Figure() {
        this.position = new Position(0,0);
        this.lastMove = Direction.NONE;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setLastMove(Direction lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return Objects.equals(position, figure.position) && lastMove == figure.lastMove;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Figure{position=%s, lastMove=%s}", position, lastMove);
    }
}
