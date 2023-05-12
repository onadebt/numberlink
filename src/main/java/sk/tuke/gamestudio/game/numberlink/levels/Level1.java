package sk.tuke.gamestudio.game.numberlink.levels;

import sk.tuke.gamestudio.game.numberlink.field.NumberTile;
import sk.tuke.gamestudio.game.numberlink.field.PathTile;
import sk.tuke.gamestudio.game.numberlink.field.Tile;

public class Level1 implements Level {
    private static final int numCols = 3, numRows = 4, numPaths = 2, score = 1;

    private final Tile[][] tiles = {
            {new NumberTile(1), new NumberTile(2), new NumberTile(1)},
            {new PathTile(), new PathTile(), new PathTile()},
            {new PathTile(), new NumberTile(2), new PathTile()},
            {new PathTile(), new PathTile(), new PathTile()}
    };

    public Level1() {
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
//    public boolean updateLvl(int x, int y, int changeTile) {
//        if (tiles[y][x] instanceof PathTile) {
//            ((PathTile) tiles[y][x]).setNumber(changeTile);
//            return true;
//        } else {
//            System.out.println("You can't change that tile");
//            return false;
//        }
//    }
}
