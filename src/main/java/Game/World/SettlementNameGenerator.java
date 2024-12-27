package Game.World;

import java.util.Random;

import Game.World.World.Terrain;

public class SettlementNameGenerator {

    // Define terrain-specific prefixes and suffixes
    private static final String[] PLAINS_PREFIXES = {"Golden", "Wind", "Bright", "Sunny", "Meadow", "Flat"};
    private static final String[] PLAINS_SUFFIXES = {"field", "stead", "plain", "cross", "holm", "hill"};

    private static final String[] FOREST_PREFIXES = {"Oak", "Willow", "Briar", "Thorn", "Shadow", "Green"};
    private static final String[] FOREST_SUFFIXES = {"wood", "grove", "hollow", "briar", "leaf", "shade"};

    private static final String[] MOUNTAIN_PREFIXES = {"Iron", "Stone", "Granite", "High", "Storm", "Frost"};
    private static final String[] MOUNTAIN_SUFFIXES = {"peak", "spire", "cliff", "rock", "forge", "crag"};

    private static final String[] RIVER_PREFIXES = {"Blue", "Silver", "Clear", "Swift", "Drift", "Tide"};
    private static final String[] RIVER_SUFFIXES = {"water", "brook", "ford", "stream", "haven", "port"};

    //private static final String[] MODIFIERS = {"North", "South", "East", "West", "Great", "Little", "New", "Old", "High", "Low"};

    public static String generateName(Terrain terrain) {

        Random random = new Random();
        String[] prefixes, suffixes;

        switch (terrain) {
            case Plains -> { prefixes = PLAINS_PREFIXES; suffixes = PLAINS_SUFFIXES; }
            case Forest -> { prefixes = FOREST_PREFIXES; suffixes = FOREST_SUFFIXES; }
            case River -> { prefixes = RIVER_PREFIXES; suffixes = RIVER_SUFFIXES; }
            case Mountain -> { prefixes = MOUNTAIN_PREFIXES; suffixes = MOUNTAIN_SUFFIXES; }
            default -> { prefixes = PLAINS_PREFIXES; suffixes = PLAINS_SUFFIXES; }
        }

        String prefix = prefixes[random.nextInt(prefixes.length)];
        String suffix = suffixes[random.nextInt(suffixes.length)];

        return prefix + suffix;

    }

}
