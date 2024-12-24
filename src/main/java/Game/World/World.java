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
            Settlement connectingTo = getRandomSettlement();
            while (settlement == connectingTo) {
                log.info("Picking new settlement.");
                connectingTo = getRandomSettlement();
            }

            log.info("Connecting " + settlement.name + " to " + connectingTo.name);

            int dirX = Integer.compare(connectingTo.worldSpace.xCoord, settlement.worldSpace.xCoord);
            int dirY = Integer.compare(connectingTo.worldSpace.yCoord, settlement.worldSpace.yCoord);

            Direction direction = Direction.fromValues(dirX, dirY);

            createRoad(settlement, connectingTo);

            settlement.signposts.put(direction, connectingTo);
        }
    }

    private void createRoad(Settlement from, Settlement to) {
        int fromX = from.worldSpace.xCoord;
        int fromY = from.worldSpace.yCoord;
        int toX = to.worldSpace.xCoord;
        int toY = to.worldSpace.yCoord;

        while (fromX != toX || fromY != toY) {
            log.info("Moving from X:" + fromX + " Y:" + fromY);
            log.info("Moving to X:" + toX + " Y:" + toY);

            int diffX = (toX - fromX >= 1) ? 1 : (toX - fromX <= -1) ? -1 : 0;
            int diffY = (toY - fromY >= 1) ? 1 : (toY - fromY <= -1) ? -1 : 0;
            log.info("Direction X:" + diffX + " Y:" + diffY);
            Direction direction = null;
            try {
                direction = Direction.fromValues(diffX, diffY);
            } catch (IllegalArgumentException e) { log.error("Could not find a direction, while creating a road."); }
            log.info("Direction:" + direction);

            log.info("Creating signpost on World Space X:" + fromX + " Y:" + fromY);
            WorldSpace worldSpace = getWorldSpace(fromX, fromY);
            worldSpace.signposts.put(direction, to);
            log.info("Signpost:" + worldSpace.signposts.get(direction).name + " dir:" + direction);

            fromX += diffX;
            fromY += diffY;

            WorldSpace roadWorldSpace = getWorldSpace(fromX, fromY);
            roadWorldSpace.feature = "Road";
            log.info("Created Road at X:" + fromX + " Y:" + fromY);

            Direction opDirection = Direction.getOppositeDirection(direction);
            log.info("Creating " + opDirection + " signpost on World Space X:" + fromX + " Y:" + fromY);
            roadWorldSpace.signposts.put(opDirection, from);
            log.info(roadWorldSpace + " Signpost:" + roadWorldSpace.signposts.get(opDirection).name + " dir:" + opDirection);
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
