package sk.tuke.gamestudio.game.numberlink.levels;

import sk.tuke.gamestudio.game.numberlink.field.Tile;

public interface Level {
    int getNumRows();

    int getNumCols();

    int getNumOfPaths();

    int getScore();

    Tile[][] getTiles();
}
