package Game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Game.World.Settlement.Direction;

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
        createSettlements(2);

        for (Settlement settlement : settlements) {
            System.out.println(settlement.name + " at X:" + settlement.worldSpace.xCoord + " Y:" + settlement.worldSpace.yCoord );
        }
    }

    private void generateTerrain() {
        Random random = new Random();
        Terrain[] terrains = Terrain.values();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Terrain terrain = terrains[random.nextInt(terrains.length)];
                grid[y][x] = new WorldSpace(terrain,x,y);
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

    public Settlement getRandomSettlement() {
        Random random = new Random();
        return settlements.get(random.nextInt(settlements.size()));
    }

    public void connectSettlements() {
        for (Settlement settlement : settlements) {
            Settlement connectingTo = getRandomSettlement();
            while (settlement == connectingTo) {
                System.out.println("Picking new settlement.");
                connectingTo = getRandomSettlement();
            }

            System.out.println("Connecting " + settlement.name + " to " + connectingTo.name);

            int dirX = Integer.compare(connectingTo.worldSpace.xCoord, settlement.worldSpace.xCoord);
            int dirY = Integer.compare(connectingTo.worldSpace.yCoord, settlement.worldSpace.yCoord);

            Settlement.Direction direction = Settlement.Direction.fromValues(dirX, dirY);

            createRoad(
                    settlement.worldSpace.xCoord,
                    settlement.worldSpace.yCoord,
                    connectingTo.worldSpace.xCoord,
                    connectingTo.worldSpace.yCoord
            );

            settlement.signposts.put(direction, connectingTo);
        }
    }

    public void createRoad(int x1, int y1, int x2, int y2) {
        int currentX = x1;
        int currentY = y1;

        while (currentX != x2 || currentY != y2) {
            System.out.println(currentX + " " + currentY + " " + x2 + " " + y2);

            if (currentX < x2) currentX++;
            else if (currentX > x2) currentX--;

            if (currentY < y2) currentY++;
            else if (currentY > y2) currentY--;

            getWorldSpace(currentX, currentY).feature = "Road";
            System.out.println("Created Road at X:" + currentX + " Y:" + currentY);
        }
    }

    public HashMap<Direction, WorldSpace> getSurroundingSpaces(WorldSpace space) {
        HashMap<Direction, WorldSpace> surroundingSpaces = new HashMap<>();

        for (Direction dir : Direction.values()) {
            getWorldSpaceOffset(space, dir);
        }

        return surroundingSpaces;
    }

    public WorldSpace getWorldSpace(int x, int y) {
        if (x >= 0 && x < columns && y >= 0 && y < rows)
            return grid[y][x];
        else
            return null;

    }

    public WorldSpace getWorldSpaceOffset(WorldSpace space, Direction direction) {
        int x = space.xCoord + direction.getX();
        int y = space.yCoord + direction.getY();

        return getWorldSpace(x, y);
    }

    public void printTerrainMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print("[" + getWorldSpace(x, y).getTerrainSymbol() + "] ");
            }
            System.out.println();
        }
    }

    public void printSettlementMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (getWorldSpace(x, y).settlement == null)
                    System.out.print("[ ] ");
                else
                    System.out.print("[X] ");
            }
            System.out.println();
        }
    }

    public void printFeatureMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (getWorldSpace(x, y).feature == null)
                    System.out.print("[ ] ");
                else
                    System.out.print("[R] ");
            }
            System.out.println();
        }
    }

    enum Terrain { Plains, Forest, River, Mountain }

}
