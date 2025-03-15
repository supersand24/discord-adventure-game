package Game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//An expandable 2D Array to keep track of different Locations within the Game Session.
public class World {

    private final Logger log = LoggerFactory.getLogger(World.class);

    int rows;
    int columns;
    WorldSpace[][] grid;
    List<Settlement> settlements;

    public World(int rows, int columns, int numOfSettlements) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new WorldSpace[rows][columns];
        this.settlements = new ArrayList<>();
        generateTerrain();
        createSettlements(numOfSettlements);

        for (Settlement settlement : settlements) {
            log.info(settlement.name + " at X:" + settlement.worldSpace.xCoord + " Y:" + settlement.worldSpace.yCoord);
        }
    }

    public void generateTerrain() {
        Random random = new Random();
        Terrain[] terrains = Terrain.values();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Terrain terrain = terrains[random.nextInt(terrains.length)];
                grid[y][x] = new WorldSpace(terrain,x,y);
            }
        }
    }

    public void createSettlements(int number) {
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
            for (Signpost signpost : settlement.worldSpace.signposts.values()) {
                for (Settlement signpostSettlement : signpost.getSettlements()) {
                    connectables.remove(signpostSettlement);
                }
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
        //Save Start and Finish
        int fromX = from.worldSpace.xCoord;
        int fromY = from.worldSpace.yCoord;
        int toX = to.worldSpace.xCoord;
        int toY = to.worldSpace.yCoord;

        Direction direction = null;

        while (fromX != toX || fromY != toY) {

            //Find Direction to Navigate
            int dirX = Integer.compare(toX, fromX);
            int dirY = Integer.compare(toY, fromY);
            try { direction = Direction.fromValues(dirX, dirY); }
            catch (IllegalArgumentException e) { log.error("Could not find a direction, while creating a road."); }
            log.info("Direction:" + direction);

            //Place Signpost pointing Forwards
            WorldSpace worldSpace = getWorldSpace(fromX, fromY);
            //worldSpace.signposts.put(direction, to);
            worldSpace.addSettlementSignpost(direction, to);
            //log.info(worldSpace + " Signpost:" + worldSpace.signposts.get(direction).name + " dir:" + direction);

            //Mark World Space as Road
            worldSpace.feature = "Road";
            log.info("Created Road at " + worldSpace);

            //Move Toward Desired Direction
            fromX += dirX;
            fromY += dirY;

            //Place Signpost pointing Backwards
            worldSpace = getWorldSpace(fromX, fromY);
            direction = Direction.getOppositeDirection(direction);
            //worldSpace.signposts.put(direction, from);
            worldSpace.addSettlementSignpost(direction, from);
            //log.info(worldSpace + " Signpost:" + worldSpace.signposts.get(direction).name + " dir:" + direction);

            //Check World Space for existing Signposts
            /*System.out.println("Checking for existing signposts with name " + to.name);
            for (Settlement settlement : worldSpace.signposts.values()) {
                System.out.println("!!!! -> " + settlement.name);
                if (settlement == to) {
                    System.out.println("SAME SETTLEMENT, STOP ROAD HERE!!!!!");
                    //fromX = toX;
                    //fromY = toY;
                }
            }*/

        }

        //to.signposts.put(direction, from);
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
