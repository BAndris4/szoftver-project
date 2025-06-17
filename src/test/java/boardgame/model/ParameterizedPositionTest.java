package boardgame.model;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedPositionTest {
    void assertPosition(int expectedRow, int expectedColumn, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedColumn, position.column())
        );
    }

    static Stream<Position> positionProvider() {
        return Stream.of(new Position(0, 0),
                new Position(0, 2),
                new Position(2, 0),
                new Position(2, 2));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void getDirectionFromPositionChangeTest(Position position){
        assertEquals(Direction.RIGHT, Position.getDirectionFromPositionChange(position, new Position(position.row(),position.column()+1)));
        assertEquals(Direction.LEFT, Position.getDirectionFromPositionChange(position, new Position(position.row(),position.column()-1)));
        assertEquals(Direction.UP, Position.getDirectionFromPositionChange(position, new Position(position.row()-1,position.column())));
        assertEquals(Direction.DOWN, Position.getDirectionFromPositionChange(position, new Position(position.row()+1,position.column())));
        assertEquals(Direction.NONE, Position.getDirectionFromPositionChange(position, new Position(position.row(), position.column())));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void getNewPositionTest(Position position){
        for (int i = 0; i < 8; i++) {
            assertPosition(position.row(), position.column()-i, Position.getNewPosition(position, Direction.LEFT, i));
        }
        for (int i = 0; i < 8; i++) {
            assertPosition(position.row(), position.column()+i, Position.getNewPosition(position, Direction.RIGHT, i));
        }
        for (int i = 0; i < 8; i++) {
            assertPosition(position.row()-i, position.column(), Position.getNewPosition(position, Direction.UP, i));
        }
        for (int i = 0; i < 8; i++) {
            assertPosition(position.row()+i, position.column(), Position.getNewPosition(position, Direction.DOWN, i));
        }
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testToString(Position position){
        assertEquals(String.format("(%d,%d)", position.row(), position.column()), position.toString());
    }
}
