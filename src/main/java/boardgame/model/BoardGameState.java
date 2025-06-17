package boardgame.model;

import puzzle.TwoPhaseMoveState;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BoardGameState implements TwoPhaseMoveState<Position> {

    private static final int[][] table = new int[][]{
            {3, 5, 0, 2, 1, 2, 3, 4},
            {1, 2, 2, 1, 4, 5, 2, 0},
            {2, 0, 1, 3, 4, 3, 2, 1},
            {4, 4, 0, 2, 3, 0, 5, 2},
            {4, 1, 0, 3, 3, 2, 4, 3},
            {1, 0, 2, 2, 3, 0, 1, 0},
            {4, 0, 2, 2, 1, 4, 0, 1},
            {2, 2, 0, 4, 3, 5, 4, -1}};

    private Figure figure1;
    private Figure figure2;

    public BoardGameState() {
        this.figure1 = new Figure();
        this.figure2 = new Figure();
    }

    @Override
    public boolean isSolved() {
        return figure1.getPosition().equals(new Position(7,7)) && figure2.getPosition().equals(new Position(7,7));
    }

    @Override
    public Set<TwoPhaseMove<Position>> getLegalMoves() {
        var moves = new HashSet<TwoPhaseMove<Position>>();

        var currentPosition = figure1.getPosition();
        for (Direction direction : Direction.getMovableDirections()) {
            var move = new TwoPhaseMove<Position>(currentPosition, Position.getNewPosition(currentPosition, direction, table[currentPosition.row()][currentPosition.column()]));
            if (isLegalMove(move)) {
                moves.add(move);
            }
        }

        currentPosition = figure2.getPosition();
        for (Direction direction : Direction.getMovableDirections()) {
            var move = new TwoPhaseMove<Position>(currentPosition, Position.getNewPosition(currentPosition, direction, table[currentPosition.row()][currentPosition.column()]));
            if (isLegalMove(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public BoardGameState clone() {
        BoardGameState copy = new BoardGameState();
        copy.figure1 = new Figure();
        copy.figure1.setPosition(this.figure1.getPosition());
        copy.figure1.setLastMove(this.figure1.getLastMove());
        copy.figure2 = new Figure();
        copy.figure2.setPosition(this.figure2.getPosition());
        copy.figure2.setLastMove(this.figure2.getLastMove());
        return copy;
    }

    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return (figure1.getPosition().equals(position) && figure1.getLastMove().equals(Direction.NONE)) ||
                (figure2.getPosition().equals(position) && figure2.getLastMove().equals(Direction.NONE));
    }


    public boolean isValidPosition(Position position){
        return position.row() >= 0 && position.column() >= 0 && position.column() <= 7 && position.row() <= 7;
    }

    public Direction getOtherFiguresLastMove(Position from){
        if (from.equals(figure1.getPosition())){
            return figure2.getLastMove();
        } else {
            return figure1.getLastMove();
        }
    }

    public List<Direction> getLegalDirections(Position from){
        Direction otherFiguresLastMove = getOtherFiguresLastMove(from);

        if (otherFiguresLastMove.equals(Direction.UP) || otherFiguresLastMove.equals(Direction.DOWN)) {
            return List.of(Direction.LEFT, Direction.RIGHT);
        } else if (otherFiguresLastMove.equals(Direction.RIGHT) || otherFiguresLastMove.equals(Direction.LEFT)) {
            return List.of(Direction.UP, Direction.DOWN);
        } else {
            return List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT);
        }
    }

    @Override
    public boolean isLegalMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        if (isValidPosition(positionTwoPhaseMove.to()) && isLegalToMoveFrom(positionTwoPhaseMove.from())){
            var from = positionTwoPhaseMove.from();
            var legalDirections = getLegalDirections(from);
            for (Direction direction : legalDirections) {
                if (Position.getNewPosition(from, direction, table[from.row()][from.column()]).equals(positionTwoPhaseMove.to())){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        if (positionTwoPhaseMove.from().equals(figure1.getPosition())){
            figure1.setPosition(positionTwoPhaseMove.to());
            figure1.setLastMove(Position.getDirectionFromPositionChange(positionTwoPhaseMove.from(), positionTwoPhaseMove.to()));
        } else {
            figure2.setPosition(positionTwoPhaseMove.to());
            figure2.setLastMove(Position.getDirectionFromPositionChange(positionTwoPhaseMove.from(), positionTwoPhaseMove.to()));
        }
        if (!(figure1.getLastMove().equals(Direction.NONE)) && !(figure2.getLastMove().equals(Direction.NONE))){
            figure1.setLastMove(Direction.NONE);
            figure2.setLastMove(Direction.NONE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGameState that = (BoardGameState) o;
        return Objects.equals(figure1, that.figure1) && Objects.equals(figure2, that.figure2);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BoardGameState{");
        sb.append("figure1=").append(figure1);
        sb.append(", figure2=").append(figure2);
        sb.append('}');
        return sb.toString();
    }
}
