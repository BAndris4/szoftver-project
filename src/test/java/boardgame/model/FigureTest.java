package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FigureTest {

    @Test
    public void defaultFigureTest(){
        assertEquals(new Figure(new Position(0,0), Direction.NONE), new Figure());
    }

}
