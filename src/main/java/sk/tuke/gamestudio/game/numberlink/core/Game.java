package sk.tuke.gamestudio.game.numberlink.core;

import sk.tuke.gamestudio.game.numberlink.field.NumberTile;
import sk.tuke.gamestudio.game.numberlink.field.PathTile;
import sk.tuke.gamestudio.game.numberlink.field.Tile;
import sk.tuke.gamestudio.game.numberlink.levels.Level;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private final Level level;
    private Tile prevTile;
    private final int[][] tilesIndexes;
    private ArrayList<LinkedList<Tile>> list;


    public Game(Level level) {
        this.level = level;

        list = new ArrayList<>();
        for (int i = 0; i < level.getNumOfPaths() + 1; i++) {
            list.add(new LinkedList<>());
        }

        tilesIndexes = new int[level.getNumRows()][level.getNumCols()];
        int counter = 0;
        for (int i = 0; i < level.getNumRows(); i++) { // 2d array with unique index of each tile
            for (int j = 0; j < level.getNumCols(); j++) {
                tilesIndexes[i][j] = counter++;
            }
        }
    }

    public boolean areTilesNeighbours(int inputX, int inputY, int indexX, int indexY) {
        if (indexX == 0){
            return tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] + 1 ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] + level.getNumCols() ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] - level.getNumCols();
        } else if (indexX == level.getNumCols() - 1){
            return tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] - 1 ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] + level.getNumCols() ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] - level.getNumCols();
        } else {
            return tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] + 1 ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] - 1 ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] + level.getNumCols() ||
                    tilesIndexes[inputY][inputX] == tilesIndexes[indexY][indexX] - level.getNumCols();
        }
    }


    public boolean nextMove(int x, int y) {
        if ((x < 0 || x >= level.getNumCols()) ||
                (y < 0 || y >= level.getNumRows())) { // boundaries check
            return false;
        }

        Tile currTile = level.getTiles()[y][x];

        if (currTile instanceof NumberTile) { // if currTile is NumberTile it is whether start or ending of the path
            if (list.get(currTile.getNumber()).isEmpty()) { // start of path
                listAdd(currTile);
                prevTile = currTile;
                return true;
            } else if (listContains(currTile)) { // prevent duplicates
                prevTile = list.get(currTile.getNumber()).getLast();
                return true;
            } else if (areTilesNeighbours(currTile.getX(), currTile.getY(), prevTile.getX(), prevTile.getY())) { // end of path
                listAdd(currTile);
                prevTile = currTile;
                return true;
            }
        } else if (currTile instanceof PathTile) {
            if (listContains(currTile)) {
                prevTile = list.get(currTile.getNumber()).getLast();
                return true;
            } else if (prevTile == null || (prevTile instanceof NumberTile && getListFirst(prevTile) != getListLast(prevTile))) {
                return false;
            } else if (areTilesNeighbours(currTile.getX(), currTile.getY(), prevTile.getX(), prevTile.getY())) {
                ((PathTile) currTile).setNumber(prevTile.getNumber()); // current tile will have the same number as previous
                listAdd(currTile);
                prevTile = currTile;
                return true;
            }
        }

        return false;
    }

    public boolean nextMove(String input) {
        if (input.equalsIgnoreCase("erase")) {
            if (prevTile == null) return false;
            erasePath(prevTile.getX(), prevTile.getY());
            return true;
        } else if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
            return true;
        }
        return false;
    }


    public void erasePath(int x, int y) {
        Tile tile = level.getTiles()[y][x];
        int eraseNum = tile.getNumber(); // because of setting PathTile to 0 in further steps

        for (Tile eachTile : list.get(tile.getNumber())) {
            if (eachTile instanceof PathTile) ((PathTile) eachTile).setNumber(0); // clears PathTiles of one number
        }

        list.get(eraseNum).clear();
    }

    public boolean isWon() {
        for (int i = 0; i < level.getNumRows(); i++) {
            for (int j = 0; j < level.getNumCols(); j++) {
                if (level.getTiles()[i][j].getNumber() == 0) {
                    return false;
                }
            }
        }

        int countRightPaths = 0;
        for (int i = 1; i <= level.getNumOfPaths(); i++) {
            if (list.get(i).size() > 0) {
                if (list.get(i).getFirst() instanceof NumberTile && list.get(i).getLast() instanceof NumberTile) {
                    countRightPaths++;
                }
            }
        }

        return countRightPaths == level.getNumOfPaths();
    }

    public boolean listContains(Tile tile) {
        return list.get(tile.getNumber()).contains(tile);
    }

    public void listAdd(Tile tile) {
        list.get(tile.getNumber()).add(tile);
    }

    public Tile getListLast(Tile tile) {
        return list.get(tile.getNumber()).getLast();
    }

    public Tile getListFirst(Tile tile) {
        return list.get(tile.getNumber()).getFirst();
    }

    public Level getLevel() {
        return level;
    }
}
