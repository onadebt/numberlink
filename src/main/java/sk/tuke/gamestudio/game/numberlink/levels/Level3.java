package sk.tuke.gamestudio.game.numberlink.levels;

import sk.tuke.gamestudio.game.numberlink.field.NumberTile;
import sk.tuke.gamestudio.game.numberlink.field.PathTile;
import sk.tuke.gamestudio.game.numberlink.field.Tile;

public class Level3 implements Level {
    private static final int numCols = 6, numRows = 6, numPaths = 5, score = 3;

    private final Tile[][] tiles = {
            {new NumberTile(3), new PathTile(), new NumberTile(4), new NumberTile(1), new NumberTile(2), new NumberTile(1)},
            {new NumberTile(4), new NumberTile(3), new PathTile(), new PathTile(), new PathTile(), new PathTile()},
            {new PathTile(), new NumberTile(5), new PathTile(), new PathTile(), new PathTile(), new PathTile()},
            {new PathTile(), new PathTile(), new PathTile(), new PathTile(), new PathTile(), new PathTile()},
            {new PathTile(), new NumberTile(5), new PathTile(), new PathTile(), new NumberTile(2), new PathTile()},
            {new PathTile(), new PathTile(), new PathTile(), new PathTile(), new PathTile(), new PathTile()}
    };

    public Level3() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                tiles[i][j].setX(j);
                tiles[i][j].setY(i);
            }
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getNumOfPaths() {
        return numPaths;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
