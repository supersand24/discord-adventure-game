package Game.World;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

        boolean isConnected = false;
        for (Settlement settlement : world.settlements) {
            if (!settlement.worldSpace.signposts.isEmpty()) {
                isConnected = true;
                break;
            }
        }
        assertTrue(isConnected, "At least one settlement should have a connection.");
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
