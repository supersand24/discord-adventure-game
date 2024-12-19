package Game.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//An expandable 2D Array to keep track of different Locations within the Game Session.
public class World {

    int rows;
    int columns;
    WorldSpace[][] grid;
    List<Settlement> settlements;

    public World(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new WorldSpace[rows][columns];
        this.settlements = new ArrayList<>();
        generateTerrain();
        createSettlements(3);

        for (Settlement settlement : settlements) {
            System.out.println(settlement.name);
        }
    }

    private void generateTerrain() {
        Random random = new Random();
        Terrain[] terrains = Terrain.values();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Terrain terrain = terrains[random.nextInt(terrains.length)];
                grid[i][j] = new WorldSpace(terrain);
            }
        }
    }

    private void createSettlements(int number) {
        Random random = new Random();
        List<WorldSpace> selectedWorldSpaces = new ArrayList<>();

        while (selectedWorldSpaces.size() < number) {
            int randomRow = random.nextInt(rows);
            int randomColumn = random.nextInt(columns);
            if (!selectedWorldSpaces.contains(grid[randomRow][randomColumn])) {
                selectedWorldSpaces.add(grid[randomRow][randomColumn]);
            }
        }

        for (WorldSpace worldSpace : selectedWorldSpaces) {
            settlements.add(worldSpace.createSettlement());
        }

    }

    public void systemPrintMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("[" + grid[i][j].getTerrainSymbol() + "] ");
            }
            System.out.println();
        }
    }

    enum Terrain { Plains, Forest, River, Mountain}

}
