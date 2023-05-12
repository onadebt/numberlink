package sk.tuke.gamestudio.game.numberlink.field;

public class NumberTile implements Tile {
    private int x, y;
    private final int number;

    public NumberTile(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
