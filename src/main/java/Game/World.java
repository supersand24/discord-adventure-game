package Game;

//An expandable 2D Array to keep track of different Locations within the Game Session.
public class World {

    int rows;
    int columns;
    int[][] grid;

    public World(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new int[rows][columns];
    }

    public void systemPrintMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("[" + grid[i][j] + "] ");
            }
            System.out.println();
        }
    }

}
