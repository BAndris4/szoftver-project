package boardgame.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void getRow() {
        assertEquals(0, Direction.NONE.getRow());
        assertEquals(0, Direction.LEFT.getRow());
        assertEquals(0, Direction.RIGHT.getRow());
        assertEquals(-1, Direction.UP.getRow());
        assertEquals(1, Direction.DOWN.getRow());
    }
    @Test
    void getColumn() {
        assertEquals(0, Direction.NONE.getColumn());
        assertEquals(-1, Direction.LEFT.getColumn());
        assertEquals(1, Direction.RIGHT.getColumn());
        assertEquals(0, Direction.UP.getColumn());
        assertEquals(0, Direction.DOWN.getColumn());
    }

    @Test
    void getMovableDirections() {
        assertEquals(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT), Direction.getMovableDirections());
    }
}
