package Game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Game.GameSession;

//An expandable 2D Array to keep track of different Locations within the Game Session.
public class World {

    private final Logger log = LoggerFactory.getLogger(GameSession.class);

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
            log.info(settlement.name + " at X:" + settlement.worldSpace.xCoord + " Y:" + settlement.worldSpace.yCoord);
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
            List<Settlement> connectables = new ArrayList<>(settlements);
            connectables.remove(settlement);
            for (Settlement signpostSettlement : settlement.signposts.values()) {
                connectables.remove(signpostSettlement);
            }
            if (connectables.size() > 0) {
                System.out.println("More than 0 settlements.");
                Random random = new Random();
                Settlement connectingTo = connectables.get(random.nextInt(connectables.size()));
                log.info("Connecting " + settlement.name + " to " + connectingTo.name);
                createRoad(settlement, connectingTo);
            } else {
                System.out.println("No possible settlements to connect to.");
            }
        }
    }

    private void createRoad(Settlement from, Settlement to) {
        int fromX = from.worldSpace.xCoord;
        int fromY = from.worldSpace.yCoord;
        int toX = to.worldSpace.xCoord;
        int toY = to.worldSpace.yCoord;

        int dirX = Integer.compare(to.worldSpace.xCoord, from.worldSpace.xCoord);
        int dirY = Integer.compare(to.worldSpace.yCoord, from.worldSpace.yCoord);

        Direction direction = Direction.fromValues(dirX, dirY);

        from.signposts.put(direction, to);

        while (fromX != toX || fromY != toY) {
            log.info("Moving from X:" + fromX + " Y:" + fromY);
            log.info("Moving to X:" + toX + " Y:" + toY);

            int diffX = (toX - fromX >= 1) ? 1 : (toX - fromX <= -1) ? -1 : 0;
            int diffY = (toY - fromY >= 1) ? 1 : (toY - fromY <= -1) ? -1 : 0;
            log.info("Direction X:" + diffX + " Y:" + diffY);
            try {
                direction = Direction.fromValues(diffX, diffY);
            } catch (IllegalArgumentException e) { log.error("Could not find a direction, while creating a road."); }
            log.info("Direction:" + direction);

            log.info("Creating signpost on World Space X:" + fromX + " Y:" + fromY);
            WorldSpace worldSpace = getWorldSpace(fromX, fromY);
            worldSpace.signposts.put(direction, to);
            log.info(worldSpace + " Signpost:" + worldSpace.signposts.get(direction).name + " dir:" + direction);

            fromX += diffX;
            fromY += diffY;

            WorldSpace roadWorldSpace = getWorldSpace(fromX, fromY);
            roadWorldSpace.feature = "Road";
            log.info("Created Road at " + roadWorldSpace);

            direction = Direction.getOppositeDirection(direction);
            log.info("Creating " + direction + " signpost on " + roadWorldSpace);
            roadWorldSpace.signposts.put(direction, from);
            log.info(roadWorldSpace + " Signpost:" + roadWorldSpace.signposts.get(direction).name + " dir:" + direction);
        }

        to.signposts.put(direction, from);
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
