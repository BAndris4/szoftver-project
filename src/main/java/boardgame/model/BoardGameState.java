package boardgame.model;

import puzzle.TwoPhaseMoveState;

import java.util.HashSet;
import java.util.List;
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

    public static List<Direction> getMovableDirections() {
        return List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public Set<TwoPhaseMove<Position>> getLegalMoves() {
        var moves = new HashSet<TwoPhaseMove<Position>>();

        var currentPosition = figure1.getPosition();
        for (Direction direction : getMovableDirections()) {
            var move = new TwoPhaseMove<Position>(currentPosition, getNewPosition(currentPosition, direction, table[currentPosition.row()][currentPosition.column()]));
            if (isLegalMove(move)) {
                moves.add(move);
            }
        }

        currentPosition = figure2.getPosition();
        for (Direction direction : getMovableDirections()) {
            var move = new TwoPhaseMove<Position>(currentPosition, getNewPosition(currentPosition, direction, table[currentPosition.row()][currentPosition.column()]));
            if (isLegalMove(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public TwoPhaseMoveState<Position> clone() {
        return null;
    }

    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return figure1.getPosition().equals(position) || figure2.getPosition().equals(position);
    }

    private Position getNewPosition(Position from, Direction direction, int steps) {
        int newRow = from.row() + direction.getRow() * steps;
        int newColumn = from.column() + direction.getColumn() * steps;
        return new Position(newRow, newColumn);
    }

    private boolean isValidPosition(Position position){
        return position.row() >= 0 && position.column() >= 0 && position.column() <= 7 && position.row() <= 7;
    }

    private Direction getOtherFiguresLastMove(Position from){
        if (from.equals(figure1.getPosition())){
            return figure2.getLastMove();
        } else {
            return figure1.getLastMove();
        }
    }

    private List<Direction> getLegalDirections(Position from){
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
        if (isValidPosition(positionTwoPhaseMove.to())){
            var from = positionTwoPhaseMove.from();
            var legalDirections = getLegalDirections(from);
            for (Direction direction : legalDirections) {
                if (getNewPosition(from, direction, table[from.row()][from.column()]).equals(positionTwoPhaseMove.to())){
                    return true;
                }
            }
        }
        return false;
    }

    private Direction getDirectionFromPositionChange(Position from, Position to) {
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

    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        if (positionTwoPhaseMove.from().equals(figure1.getPosition())){
            figure1.setPosition(positionTwoPhaseMove.to());
            figure1.setLastMove(getDirectionFromPositionChange(positionTwoPhaseMove.from(), positionTwoPhaseMove.to()));
            figure2.setLastMove(Direction.NONE);
        } else {
            figure2.setPosition(positionTwoPhaseMove.to());
            figure2.setLastMove(getDirectionFromPositionChange(positionTwoPhaseMove.from(), positionTwoPhaseMove.to()));
            figure1.setLastMove(Direction.NONE);
        }
    }
}
