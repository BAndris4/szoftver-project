package boardgame.model;

import puzzle.TwoPhaseMoveState;

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
        return Set.of();
    }

    @Override
    public TwoPhaseMoveState<Position> clone() {
        return null;
    }

    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return figure1.getPosition().equals(position) || figure2.getPosition().equals(position);
    }

    @Override
    public boolean isLegalMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        return false;
    }

    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {

    }
}
