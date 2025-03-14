import java.util.Scanner;
import java.util.List;
import java.util.Set;

import Game.GameSession;
import Game.PlayerCharacter;
import Game.World.Settlement;
import Game.World.World;
import Game.World.Location;
import Game.World.WorldSpace;
import Game.World.Direction;
import Game.World.Signpost;

@SuppressWarnings("unused")
public class MockGameSession {

    static GameSession currentGameSession;
    static int controlledPlayer = 0;

    static Scanner in;

    public static void main(String[] args) {

        currentGameSession = new GameSession();

        currentGameSession.createCharacter(0, "Steve");
        currentGameSession.createCharacter(1, "David");
        currentGameSession.createCharacter(2, "Brian");
        currentGameSession.createCharacter(3, "Gerald");

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

        for(PlayerCharacter player : currentGameSession.linkedPlayers.values()) {
            System.out.println(player.name + " was spawned at " + player.getLocation().name + ".");
        }

        in = new Scanner(System.in);

        awaitInput();

    }

    public static void awaitInput() {
        System.out.println("What would you like to do?");
        String next = in.nextLine();
        if (next.equals("quit")) System.exit(0);

        String[] parts = next.split(" "); // Split the string by spaces

        if (parts[0].startsWith("check")) {
            PlayerCharacter player = currentGameSession.getCharacter(controlledPlayer);
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
                case "check-signposts" -> {
                    WorldSpace location = player.getLocation().getWorldSpace();
                    List<Signpost> signposts = location.getSignposts();
                    if (signposts.size() > 0) {
                        if (signposts.size() == 1) {
                            Direction dir = signposts.get(0).getDirection();
                            System.out.println("There is a single signpost and it says");
                            for (Settlement settlement : signposts.get(0).getSettlements()) {
                                System.out.println(settlement.name + " is to the " + dir);
                            }
                        } else {
                            System.out.println("There are multiple signposts, they say");
                            for (Signpost signpost : signposts) {
                                for (Settlement settlement : signpost.getSettlements()) {
                                    System.out.println(settlement.name + " is to the " + signpost.getDirection());
                                }
                            }
                        }
                    } else System.out.println("There are no signposts in sight.");
                }
            }
        } else {
            switch (parts[0]) {
                case "move" -> {
                    if (parts.length > 1) {
                        Direction dir = null;
                        switch (parts[1]) {
                            case "north" -> dir = Direction.North;
                            case "northeast" -> dir = Direction.Northeast;
                            case "east" -> dir = Direction.East;
                            case "southeast" -> dir = Direction.Southeast;
                            case "south" -> dir = Direction.South;
                            case "southwest" -> dir = Direction.Southwest;
                            case "west" -> dir = Direction.West;
                            case "northwest" -> dir = Direction.Northwest;
                            default -> System.out.println("Unknown Direction, try again.");
                        }
                        if (dir != null) {
                            currentGameSession.moveCharacter(controlledPlayer, dir);
                        }
                    } else {
                        System.out.println("Which way should I go?");
                    }
                }
                case "enter" -> {
                    PlayerCharacter player = currentGameSession.getCharacter(controlledPlayer);
                    Location playerLocation = player.getLocation().getWorldSpace();
                    if (playerLocation instanceof WorldSpace worldSpace) {
                        if (worldSpace.hasSettlement()) {
                            player.moveTo(worldSpace.getSettlement());
                            System.out.println(player.name + " entered " + worldSpace.getSettlement().name + ".");
                        }
                    }
                }
                case "swap" -> {
                    if (parts.length > 1) {
                        if (currentGameSession.getCharacter(Integer.parseInt(parts[1])) != null) {
                            controlledPlayer = Integer.parseInt(parts[1]);
                            System.out.println("Swapped to " + currentGameSession.getCharacter(controlledPlayer).name);
                        }
                    } else System.out.println("Not enough arguments, need a number to swap to!");
                }
                default -> System.out.println("Unknown Command, try again...");
            }
        }

        awaitInput();
    }
    
}
