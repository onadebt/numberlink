package sk.tuke.gamestudio.game.numberlink.field;

public class BlockedTile implements Tile {
    private int x, y;

    public BlockedTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getNumber() {
        return -1;
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
