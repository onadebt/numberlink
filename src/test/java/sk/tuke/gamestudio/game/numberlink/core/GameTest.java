package sk.tuke.gamestudio.game.numberlink.core;

import org.testng.annotations.Test;
import sk.tuke.gamestudio.game.numberlink.consoleui.Frame;
import sk.tuke.gamestudio.game.numberlink.core.Game;
import sk.tuke.gamestudio.game.numberlink.field.NumberTile;
import sk.tuke.gamestudio.game.numberlink.levels.Level2;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    //        WIN STRATEGY FOR LEVEL 2

    @Test
    void Gameplay() {
        Game game = new Game(new Level2());
        assertTrue(game.nextMove(0, 0));
        assertTrue(game.nextMove(1, 0));
        assertTrue(game.nextMove(1, 1));
        assertTrue(game.nextMove(1, 2));
        assertTrue(game.nextMove(2, 2));
        assertTrue(game.nextMove(3, 2));
        Frame.drawFrame(game.getLevel().getNumCols(), game.getLevel().getNumRows(), game.getLevel().getTiles());

        assertTrue(game.nextMove(2, 1));
        assertTrue(game.nextMove(2, 0));
        assertTrue(game.nextMove(3, 0));
        assertTrue(game.nextMove(4, 0));
        Frame.drawFrame(game.getLevel().getNumCols(), game.getLevel().getNumRows(), game.getLevel().getTiles());

        assertTrue(game.nextMove(0, 1));
        assertTrue(game.nextMove(0, 2));
        assertTrue(game.nextMove(0, 3));
        assertTrue(game.nextMove(1, 3));
        assertTrue(game.nextMove(2, 3));
        assertTrue(game.nextMove(3, 3));
        assertTrue(game.nextMove(4, 3));
        assertTrue(game.nextMove(4, 2));
        assertTrue(game.nextMove(4, 1));
        assertTrue(game.nextMove(3, 1));
        Frame.drawFrame(game.getLevel().getNumCols(), game.getLevel().getNumRows(), game.getLevel().getTiles());

        assertTrue(game.nextMove(0, 4));
        assertTrue(game.nextMove(1, 4));
        assertTrue(game.nextMove(2, 4));
        assertTrue(game.nextMove(3, 4));
        assertTrue(game.nextMove(4, 4));

        assertTrue(game.isWon());
        assertTrue(game.nextMove("erase"));
        assertFalse(game.listContains(new NumberTile(4)));
        assertFalse(game.isWon());
        assertFalse(game.areTilesNeighbours(0, 0, 1, 1));
        assertTrue(game.areTilesNeighbours(0, 0, 0, 1));
        assertTrue(game.nextMove("exit"));
    }
}