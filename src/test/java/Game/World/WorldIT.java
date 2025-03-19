package Game.World;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Game.PlayerCharacter;
import Game.TestPlayerCharacter;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

class WorldIT {

    private World world;

    @BeforeEach
    void setUp() {
        world = new World(20, 20, 5);  // Create a 20x20 world with 5 settlements
    }

    @Test
    void testWorldIsGenerated() {
        assertNotNull(world, "World should be successfully initialized.");
    }

    @Test
    void testTerrainGeneration() {
        boolean hasTerrain = false;
        for (int y = 0; y < world.rows; y++) {
            for (int x = 0; x < world.columns; x++) {
                if (world.getWorldSpace(x, y) != null) {
                    hasTerrain = true;
                    break;
                }
            }
        }
        assertTrue(hasTerrain, "The world should have terrain generated.");
    }

    @Test
    void testSettlementsArePlaced() {
        List<Settlement> settlements = world.settlements;
        assertFalse(settlements.isEmpty(), "There should be at least one settlement.");
        assertEquals(5, settlements.size(), "There should be exactly 5 settlements.");
    }

    @Test
    void testGetRandomSettlement() {
        Settlement randomSettlement = world.getRandomSettlement();
        assertNotNull(randomSettlement, "Randomly selected settlement should not be null.");
        assertTrue(world.settlements.contains(randomSettlement), "Selected settlement should exist in the world.");
    }

    @Test
    void testConnectSettlements() {
        world.connectSettlements(); // Attempt to connect settlements
        List<TestPlayerCharacter> characters = new ArrayList<>();
        int playerNum = 0;

        for (Settlement settlement : world.settlements) {
            for (Signpost signpost : settlement.getWorldSpace().signposts.values()) {
                for (Settlement signpostSettlement : signpost.settlements) {
                    TestPlayerCharacter character = new TestPlayerCharacter();
                    character.name = "Player" + playerNum;
                    character.home = settlement;
                    character.goal = signpostSettlement;
                    character.moveTo(settlement);
                    settlement.addPlayer(character);
                    characters.add(character);
                    playerNum++;
                }
            }
        }

        for (int i = 0; i < 50; i++) {
            System.out.println("Tick #" + i);
            for (TestPlayerCharacter character : characters) {
                if (character.getLocation() != character.goal) {
                    List<Signpost> possibleSignposts = new ArrayList<>();
                    for (Signpost signpost : character.getLocation().getWorldSpace().signposts.values()) {
                        if (signpost.settlements.contains(character.goal)) {
                            possibleSignposts.add(signpost);
                        }
                    }

                    //Number of possible signposts
                    switch (possibleSignposts.size()) {
                        case 0 -> {
                            Location playerLocation = character.getLocation().getWorldSpace();
                            if (playerLocation instanceof WorldSpace worldSpace) {
                                if (worldSpace.hasSettlement()) {
                                    character.moveTo(worldSpace.getSettlement());
                                } else {
                                    System.out.println("I seem to be lost.");
                                }
                            }
                        }

                        case 1 -> {
                            //possibleSignposts.get(0).direction
                            Direction dir = possibleSignposts.get(0).getDirection();

                            //PlayerCharacter player = getCharacter(discordId);
                            WorldSpace movingFrom = character.getLocation().getWorldSpace();
                            WorldSpace movingTo = world.getWorldSpaceOffset(movingFrom, dir);
                            if (movingTo == null) {
                                System.out.println("I can't go " + dir + " that looks to be the edge of the world, I am not ready to commit suicide yet...");
                            } else {
                                character.getLocation().removePlayer(character);
                                character.moveTo(movingTo);
                                movingTo.addPlayer(character);
                            }

                        }
                    
                        default -> {
                            System.out.println("Too many directions to go!");
                        }
                    }

                } else {
                    System.out.println(character.name + " has arrived at " + character.goal.name + " from " + character.home.name + ".");
                    continue;
                }
                //System.out.println(testPlayerCharacter.name + " is from " + testPlayerCharacter.home.name + " is trying to get to " + testPlayerCharacter.goal.name + ".");
            }
        }

        //Verify all made it to their goal.
        boolean allMadeIt = true;

        for (TestPlayerCharacter character : characters) {
            if (character.getLocation() != character.goal) {
                allMadeIt = false;
                System.out.println(character.name + " did not make it to " + character.goal.name + " from " + character.home.name + ".");
            }
            else {
                System.out.println(character.name + " made it to " + character.goal.name + " from " + character.home.name + ".");
            }
        }

        //Verify all made it to their goal.
        
        assertTrue(allMadeIt, "All characters made it to their end destination.");
    }

    @Test
    void testWorldGenerationSpeed() {
        long startTime = System.currentTimeMillis();

        World testWorld = new World(100, 100, 10);
        testWorld.generateTerrain();
        testWorld.createSettlements(10);

        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 5000, "World generation should complete in under 5 seconds.");
    }

    @Test
    void testPrintMaps() {
        assertDoesNotThrow(() -> {
            world.printTerrainMap();
            world.printSettlementMap();
            world.printFeatureMap();
        }, "Printing maps should not throw exceptions.");
    }
}
