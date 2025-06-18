package boardgame.model;

import common.TwoPhaseMoveState;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameStateTest {

    @Test
    void isGameSolvedTest(){
        var f1 = new Figure();
        var f2 = new Figure(new Position(7,7), Direction.NONE);

        var s1 = new BoardGameState(f1,f1);
        var s2 = new BoardGameState(f1,f2);
        var s3 = new BoardGameState(f2,f1);
        var s4 = new BoardGameState(f2,f2);

        assertFalse(s1.isSolved());
        assertFalse(s2.isSolved());
        assertFalse(s3.isSolved());
        assertTrue(s4.isSolved());
    }

    @Test
    void cloneTest() {
        var f1 = new Figure();
        var f2 = new Figure(new Position(3,5), Direction.LEFT);

        var s1 = new BoardGameState(f1,f1);
        var s2 = new BoardGameState(f1,f2);
        var s3 = new BoardGameState(f2,f1);
        var s4 = new BoardGameState(f2,f2);

        assertEquals(new BoardGameState(f1,f1), s1.clone());
        assertEquals(new BoardGameState(f1,f2), s2.clone());
        assertEquals(new BoardGameState(f2,f1), s3.clone());
        assertEquals(new BoardGameState(f2,f2), s4.clone());
    }

    @Test
    void isLegalToMoveFromTest(){
        var f1 = new Figure();
        var f2 = new Figure(new Position(0,0), Direction.UP);
        var f3 = new Figure(new Position(1,1), Direction.LEFT);
        var f4 = new Figure(new Position(1,1), Direction.NONE);

        var s1 = new BoardGameState(f1,f1);
        assertTrue(s1.isLegalToMoveFrom(new Position(0,0)));
        assertFalse(s1.isLegalToMoveFrom(new Position(1,1)));

        var s2 = new BoardGameState(f1,f2);
        assertTrue(s2.isLegalToMoveFrom(new Position(0,0)));
        assertFalse(s2.isLegalToMoveFrom(new Position(1,1)));

        var s3 = new BoardGameState(f2,f2);
        assertFalse(s3.isLegalToMoveFrom(new Position(0,0)));
        assertFalse(s3.isLegalToMoveFrom(new Position(1,1)));

        var s4 = new BoardGameState(f1,f3);
        assertTrue(s4.isLegalToMoveFrom(new Position(0,0)));
        assertFalse(s4.isLegalToMoveFrom(new Position(1,1)));

        var s5 = new BoardGameState(f1,f4);
        assertTrue(s5.isLegalToMoveFrom(new Position(0,0)));
        assertTrue(s5.isLegalToMoveFrom(new Position(1,1)));

        var s6 = new BoardGameState(f2,f4);
        assertTrue(s6.isLegalToMoveFrom(new Position(1,1)));
        assertFalse(s6.isLegalToMoveFrom(new Position(0,0)));
    }

    @Test
    void isValidPositionTest(){
        var p1 = new Position(0,0);
        var p2 = new Position(4,4);
        var p3 = new Position(7,7);
        var p4 = new Position(7,8);
        var p5 = new Position(8,7);
        var p6 = new Position(8,8);
        var p7 = new Position(-1,0);
        var p8 = new Position(0,-1);
        var p9 = new Position(-1,-1);

        assertTrue(BoardGameState.isValidPosition(p1));
        assertTrue(BoardGameState.isValidPosition(p2));
        assertTrue(BoardGameState.isValidPosition(p3));
        assertFalse(BoardGameState.isValidPosition(p4));
        assertFalse(BoardGameState.isValidPosition(p5));
        assertFalse(BoardGameState.isValidPosition(p6));
        assertFalse(BoardGameState.isValidPosition(p7));
        assertFalse(BoardGameState.isValidPosition(p8));
        assertFalse(BoardGameState.isValidPosition(p9));
    }

    @Test
    void getOtherFiguresLastMoveTest(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(1,1), Direction.LEFT);
        var f3 = new Figure(new Position(2,2), Direction.RIGHT);

        var s1 = new BoardGameState(f1,f1);
        assertEquals(Direction.NONE, s1.getOtherFiguresLastMove(new Position(1,1)));
        assertThrows(IllegalArgumentException.class, () -> s1.getOtherFiguresLastMove(new Position(2,2)));

        var s2 = new BoardGameState(f1,f3);
        assertEquals(Direction.NONE, s2.getOtherFiguresLastMove(new Position(2,2)));
        assertEquals(Direction.RIGHT, s2.getOtherFiguresLastMove(new Position(1,1)));

        var s3 = new BoardGameState(f2,f3);
        assertEquals(Direction.LEFT, s3.getOtherFiguresLastMove(new Position(2,2)));
        assertEquals(Direction.RIGHT, s3.getOtherFiguresLastMove(new Position(1,1)));
    }

    @Test
    void getLegalDirectionsTest(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(2,2), Direction.UP);
        var f3 = new Figure(new Position(3,3), Direction.RIGHT);
        var f4 = new Figure(new Position(4,4), Direction.DOWN);
        var f5 = new Figure(new Position(5,5), Direction.LEFT);

        var s1 = new BoardGameState(f1,f1);
        assertEquals(Direction.getMovableDirections(), s1.getLegalDirections(new Position(1,1)));
        assertThrows(IllegalArgumentException.class, () -> s1.getLegalDirections(new Position(2,2)));

        var s2 = new BoardGameState(f2,f3);
        assertEquals(List.of(Direction.UP, Direction.DOWN), s2.getLegalDirections(new Position(2,2)));
        assertEquals(List.of(Direction.LEFT, Direction.RIGHT), s2.getLegalDirections(new Position(3,3)));

        var s3 = new BoardGameState(f4,f5);
        assertEquals(List.of(Direction.UP, Direction.DOWN), s3.getLegalDirections(new Position(4,4)));
        assertEquals(List.of(Direction.LEFT, Direction.RIGHT), s3.getLegalDirections(new Position(5,5)));
    }

    @Test
    void isLegalMoveTest(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(2,2), Direction.RIGHT);
        var f3 = new Figure(new Position(3,3), Direction.UP);
        var f4 = new Figure(new Position(2,1), Direction.NONE);

        var s1 = new BoardGameState(f1,f1);
        assertTrue(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,3))));
        assertTrue(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(3,1))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,2))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(2,1))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,0))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(0,1))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(2,2))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(-1,1))));
        assertFalse(s1.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,-1))));

        var s2 = new BoardGameState(f1,f2);
        assertTrue(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(3,1))));
        assertFalse(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,3))));
        assertFalse(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,2), new Position(2,3))));
        assertFalse(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,2), new Position(3,2))));
        assertFalse(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,2), new Position(2,1))));
        assertFalse(s2.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,2), new Position(1,2))));

        var s3 = new BoardGameState(f1,f3);
        assertTrue(s3.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,3))));
        assertFalse(s3.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(3,1))));

        var s4 = new BoardGameState(f1,f4);
        assertFalse(s4.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,1), new Position(2,1))));
    }

    @Test
    void getLegalMovesTest(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(2,2), Direction.RIGHT);
        var f3 = new Figure(new Position(3,3), Direction.UP);
        var f4 = new Figure(new Position(2,1), Direction.NONE);

        var s1 = new BoardGameState(f1,f1);
        assertEquals(Set.of(
                new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,3)),
                new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(3,1))
            ), s1.getLegalMoves());

        var s2 = new BoardGameState(f1,f2);
        assertEquals(Set.of(
                new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(3,1))
        ), s2.getLegalMoves());

        var s3 = new BoardGameState(f1,f3);
        assertEquals(Set.of(
                new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,3))
        ), s3.getLegalMoves());

        var s4 = new BoardGameState(f2,f4);
        assertEquals(Set.of(), s4.getLegalMoves());
    }

    @Test
    void makeMoveTest(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(1,1), Direction.NONE);

        var s1 = new BoardGameState(f1,f2);
        s1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,2)));
        assertEquals(new Figure(new Position(1,2), Direction.RIGHT), s1.getFigure1());
        assertEquals(new Figure(new Position(1,1), Direction.NONE), s1.getFigure2());
        s1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,2), new Position(1,1)));
        assertEquals(new Figure(new Position(1,1), Direction.LEFT), s1.getFigure1());
        s1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(2,1)));
        assertEquals(new Figure(new Position(2,1), Direction.DOWN), s1.getFigure1());
        s1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,1), new Position(1,1)));
        assertEquals(new Figure(new Position(1,1), Direction.UP), s1.getFigure1());

        var f3 = new Figure();
        var f4 = new Figure(new Position(1,1), Direction.NONE);

        var s2 = new BoardGameState(f3,f4);
        s2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,2)));
        assertEquals(new Figure(new Position(1,2), Direction.RIGHT), s2.getFigure2());
        assertEquals(new Figure(new Position(0,0), Direction.NONE), s2.getFigure1());
        s2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,2), new Position(1,1)));
        assertEquals(new Figure(new Position(1,1), Direction.LEFT), s2.getFigure2());
        s2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(2,1)));
        assertEquals(new Figure(new Position(2,1), Direction.DOWN), s2.getFigure2());
        s2.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(2,1), new Position(1,1)));
        assertEquals(new Figure(new Position(1,1), Direction.UP), s2.getFigure2());

        var f5 = new Figure(new Position(1,1), Direction.NONE);
        var f6 = new Figure(new Position(2,2), Direction.RIGHT);

        var s3 = new BoardGameState(f5, f6);
        s3.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,1), new Position(1,2)));
        assertEquals(new Figure(new Position(1,2), Direction.NONE), s3.getFigure1());
        assertEquals(new Figure(new Position(2,2), Direction.NONE), s3.getFigure2());
    }

    @Test
    void testToString(){
        var f1 = new Figure(new Position(1,1), Direction.NONE);
        var f2 = new Figure(new Position(2,2), Direction.LEFT);

        var s1 = new BoardGameState(f1,f2);
        assertEquals(String.format("BoardGameState{figure1=%s, figure2=%s}", s1.getFigure1(), s1.getFigure2()), s1.toString());
    }
}
