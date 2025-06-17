package boardgame.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterizedFigureTest {

    void assertPosition(int expectedRow, int expectedColumn, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedColumn, position.column())
        );
    }

    static Stream<Figure> figureProvider() {
        return Stream.of(new Figure(),
                new Figure(new Position(1,1), Direction.NONE),
                new Figure(new Position(1,1), Direction.LEFT),
                new Figure(new Position(1,1), Direction.UP),
                new Figure(new Position(1,1), Direction.RIGHT),
                new Figure(new Position(1,1), Direction.DOWN),
                new Figure(new Position(0,0), Direction.LEFT),
                new Figure(new Position(0,0), Direction.UP),
                new Figure(new Position(0,0), Direction.RIGHT),
                new Figure(new Position(0,0), Direction.DOWN)
        );
    }

    @Test
    void getPositionTest(){
        assertPosition(0,0, new Figure().getPosition());
        assertPosition(0,0, new Figure(new Position(0,0), Direction.NONE).getPosition());
        assertPosition(0,0, new Figure(new Position(0,0), Direction.LEFT).getPosition());
        assertPosition(1,0, new Figure(new Position(1,0), Direction.NONE).getPosition());
        assertPosition(0,1, new Figure(new Position(0,1), Direction.NONE).getPosition());
        assertPosition(1,1, new Figure(new Position(1,1), Direction.NONE).getPosition());
    }

    @Test
    void getLastMoveTest(){
        assertEquals(Direction.NONE, new Figure().getLastMove());
        assertEquals(Direction.NONE, new Figure(new Position(0,0), Direction.NONE).getLastMove());
        assertEquals(Direction.NONE, new Figure(new Position(1,1), Direction.NONE).getLastMove());
        assertEquals(Direction.LEFT, new Figure(new Position(0,0), Direction.LEFT).getLastMove());
        assertEquals(Direction.DOWN, new Figure(new Position(0,0), Direction.DOWN).getLastMove());
        assertEquals(Direction.RIGHT, new Figure(new Position(0,0), Direction.RIGHT).getLastMove());
        assertEquals(Direction.UP, new Figure(new Position(0,0), Direction.UP).getLastMove());
    }

    @ParameterizedTest
    @MethodSource("figureProvider")
    void setLastMoveTest(Figure figure){
        figure.setLastMove(Direction.NONE);
        assertEquals(Direction.NONE, figure.getLastMove());
        figure.setLastMove(Direction.LEFT);
        assertEquals(Direction.LEFT, figure.getLastMove());
        figure.setLastMove(Direction.UP);
        assertEquals(Direction.UP, figure.getLastMove());
        figure.setLastMove(Direction.DOWN);
        assertEquals(Direction.DOWN, figure.getLastMove());
        figure.setLastMove(Direction.RIGHT);
        assertEquals(Direction.RIGHT, figure.getLastMove());
    }

    @ParameterizedTest
    @MethodSource("figureProvider")
    void setPositionTest(Figure figure){
        figure.setPosition(new Position(0,0));
        assertPosition(0,0, figure.getPosition());
        figure.setPosition(new Position(1,0));
        assertPosition(1,0, figure.getPosition());
        figure.setPosition(new Position(0,1));
        assertPosition(0,1, figure.getPosition());
        figure.setPosition(new Position(1,1));
        assertPosition(1,1, figure.getPosition());
    }

    @ParameterizedTest
    @MethodSource("figureProvider")
    void hashCodeTest(Figure figure){
        assertEquals(0, figure.hashCode());
    }

    @Test
    void testEquals() {
        Figure f1 = new Figure(new Position(1, 1), Direction.UP);
        Figure f2 = new Figure(new Position(1, 1), Direction.UP);
        Figure f3 = new Figure(new Position(1, 1), Direction.DOWN);
        Figure f4 = new Figure(new Position(0, 0), Direction.UP);
        Figure f5 = f1;

        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);
        assertEquals(f1, f5);
        assertNotEquals(f1, null);
        assertNotEquals(f1, "not a Figure");
    }

    @ParameterizedTest
    @MethodSource("figureProvider")
    void testToString(Figure figure){
        assertEquals(String.format("Figure{position=%s, lastMove=%s}", figure.getPosition(), figure.getLastMove()), figure.toString());
    }
}
