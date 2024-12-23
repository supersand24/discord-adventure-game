import java.util.Scanner;
import java.util.Set;

import Game.GameSession;
import Game.PlayerCharacter;
import Game.World.Settlement;
import Game.World.World;
import Game.World.Location;
import Game.World.WorldSpace;
import Game.World.Settlement.Direction;

@SuppressWarnings("unused")
public class MockGameSession {

    static GameSession currentGameSession;

    static Scanner in;

    public static void main(String[] args) {

        currentGameSession = new GameSession();

        currentGameSession.createCharacter(5, "Steve");

        World world = currentGameSession.world;

        world.printTerrainMap();
        world.printSettlementMap();

        world.connectSettlements();

        world.printFeatureMap();

        /*
        for (Settlement settlement : currentGameSession.world.settlements) {
            for (Settlement.Direction key : settlement.signposts.keySet()) {
                System.out.println("In " + settlement.name + " there is a signpost that says " + settlement.signposts.get(key).name + " is to the " + key);
            }
        }
        */

        in = new Scanner(System.in);

        awaitInput();

    }

    public static void awaitInput() {
        System.out.println("What would you like to do?");
        String next = in.nextLine();
        if (next.equals("quit")) System.exit(0);

        String[] parts = next.split(" "); // Split the string by spaces

        if (parts[0].startsWith("check")) {
            PlayerCharacter player = currentGameSession.getCharacter(5);
            switch (parts[0]) {
                case "check-stats" -> System.out.println(player.checkStats());
                case "check-hands" -> System.out.println(player.checkHands());
                case "check-backpack" -> System.out.println(player.checkBackpack());
                case "check-location" -> {
                    if (parts.length == 1) System.out.println(player.checkLocation()); else {
                        World world = currentGameSession.world;
                        Location location = player.getLocation();
                        WorldSpace space = null;
                        if (location instanceof Settlement) {
                            Settlement settlement = (Settlement) location;
                            space = settlement.getWorldSpace();
                        } else if (location instanceof WorldSpace){
                            space = (WorldSpace) location;
                        }
                        Direction direction = null;
                        switch (parts[1]) {
                            case "north" -> direction = Direction.North;
                            case "northeast" -> direction = Direction.Northeast;
                            case "east" -> direction = Direction.East;
                            case "southeast" -> direction = Direction.Southeast;
                            case "south" -> direction = Direction.South;
                            case "southwest" -> direction = Direction.Southwest;
                            case "west" -> direction = Direction.West;
                            case "northwest" -> direction = Direction.Northwest;
                            default -> System.out.println("Unknown Direction, try again.");
                        }
                        if (direction != null) {
                            WorldSpace offsetWorldSpace = world.getWorldSpaceOffset(space, direction);
                            if (offsetWorldSpace == null) {
                                System.out.println("To the " + direction + " I see what seems to be the edge of the world, an unyielding barrier of shimmering void, where the land ends abruptly, and all beyond is nothingness.");
                            } else {
                                System.out.println("To the " + direction + " I see " + offsetWorldSpace.getTerrainDescription());
                            }
                        }
                    }
                }
            }
        }

        awaitInput();
    }
    
}
