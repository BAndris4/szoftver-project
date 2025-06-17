package boardgame.model;

import puzzle.TwoPhaseMoveState;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the state of the board game, managing two figures on an 8x8 table
 * and their moves according to game rules.
 * <p>
 * Implements {@link TwoPhaseMoveState} interface with {@link Position} as the move coordinate type.
 */
public class BoardGameState implements TwoPhaseMoveState<Position> {

    /**
     * The fixed 8x8 game table defining movement steps for each cell.
     * Each number represents how many steps a figure can move from that cell.
     */
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

    /**
     * Initializes a new {@code BoardGameState} with two figures starting at default positions.
     */
    public BoardGameState() {
        this.figure1 = new Figure();
        this.figure2 = new Figure();
    }

    /**
     * Checks whether both figures have reached the bottom-right corner of the board,
     * which means the game is solved.
     *
     * @return {@code true} if both figures are at position (7,7), {@code false} otherwise
     */
    @Override
    public boolean isSolved() {
        return figure1.getPosition().equals(new Position(7,7)) && figure2.getPosition().equals(new Position(7,7));
    }

    /**
     * Generates all legal moves for both figures based on their current positions
     * and the movement rules defined by the {@code table}.
     *
     * @return a {@link Set} of legal {@link TwoPhaseMove} objects for the current state
     */
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

    /**
     * Creates a deep copy of the current {@code BoardGameState}, including copies of
     * both figures and their last moves.
     *
     * @return a new {@code BoardGameState} instance identical to the current state
     */
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

    /**
     * Determines if a figure can legally move from the specified {@link Position}.
     * A move is legal only if the figure is at the position and it has not moved yet
     * (last move is {@link Direction#NONE}).
     *
     * @param position the {@link Position} to check for move legality
     * @return {@code true} if the figure can move from {@code position}, {@code false} otherwise
     */
    @Override
    public boolean isLegalToMoveFrom(Position position) {
        return (figure1.getPosition().equals(position) && figure1.getLastMove().equals(Direction.NONE)) ||
                (figure2.getPosition().equals(position) && figure2.getLastMove().equals(Direction.NONE));
    }


    /**
     * Checks whether the specified {@link Position} is within the boundaries of the board.
     *
     * @param position the {@link Position} to validate
     * @return {@code true} if the position is inside the 8x8 board, {@code false} otherwise
     */
    public boolean isValidPosition(Position position){
        return position.row() >= 0 && position.column() >= 0 && position.column() <= 7 && position.row() <= 7;
    }

    /**
     * Returns the last move direction of the other figure (not the one currently at {@code from}).
     *
     * @param from the current figure's {@link Position}
     * @return the {@link Direction} of the other figure's last move
     */
    public Direction getOtherFiguresLastMove(Position from){
        if (from.equals(figure1.getPosition())){
            return figure2.getLastMove();
        } else {
            return figure1.getLastMove();
        }
    }

    /**
     * Returns a list of legal movement directions for the figure at {@code from} position.
     * The allowed directions depend on the other figure's last move:
     * <ul>
     *   <li>If the other figure moved vertically (UP/DOWN), the current figure can move only horizontally (LEFT/RIGHT).</li>
     *   <li>If the other figure moved horizontally (LEFT/RIGHT), the current figure can move only vertically (UP/DOWN).</li>
     *   <li>If the other figure has not moved yet ({@link Direction#NONE}), all directions are allowed.</li>
     * </ul>
     *
     * @param from the {@link Position} of the figure to move
     * @return a {@link List} of allowed {@link Direction}s for movement
     */
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

    /**
     * Checks if the specified {@link TwoPhaseMove} is legal according to the game rules:
     * <ul>
     *   <li>The target position must be valid and inside the board.</li>
     *   <li>The move must start from a position where the figure can legally move.</li>
     *   <li>The move direction and distance must correspond to the allowed directions and the movement steps from the {@code table}.</li>
     * </ul>
     *
     * @param positionTwoPhaseMove the {@link TwoPhaseMove} to validate
     * @return {@code true} if the move is legal, {@code false} otherwise
     */
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

    /**
     * Applies the specified {@link TwoPhaseMove} to update the position and last move of
     * the figure that moves.
     * <p>
     * After both figures have moved (i.e., neither last move is {@link Direction#NONE}),
     * resets their last moves to {@link Direction#NONE} to indicate the move cycle is complete.
     *
     * @param positionTwoPhaseMove the move to apply
     */
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

    /**
     * Compares this {@code BoardGameState} with another object for equality.
     * Two states are equal if their figures are equal.
     *
     * @param o the object to compare with
     * @return {@code true} if both states are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGameState that = (BoardGameState) o;
        return Objects.equals(figure1, that.figure1) && Objects.equals(figure2, that.figure2);
    }

    /**
     * Returns the hash code for this {@code BoardGameState}.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Returns a string representation of this {@code BoardGameState},
     * showing the positions and last moves of both figures.
     *
     * @return a string describing the state
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BoardGameState{");
        sb.append("figure1=").append(figure1);
        sb.append(", figure2=").append(figure2);
        sb.append('}');
        return sb.toString();
    }
}
