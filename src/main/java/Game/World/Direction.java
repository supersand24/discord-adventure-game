package Game.World;

public enum Direction {
    North(0, -1),
    Northeast(1, -1),
    East(1, 0),
    Southeast(1, 1),
    South(0, 1),
    Southwest(-1, 1),
    West(-1, 0),
    Northwest(-1, -1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static Direction fromValues(int x, int y) {
        for (Direction dir : Direction.values()) {
            if (dir.x == x && dir.y == y) {
                return dir;
            }
        }
        throw new IllegalArgumentException("No direction for X:" + x + " Y:" + y);
    }

}
