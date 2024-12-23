package Game.World;

import java.util.*;

public class Settlement extends Location {

    WorldSpace worldSpace;

    public Map<Direction, Settlement> signposts = new HashMap<>();
    
    public Settlement(WorldSpace worldSpace) {
        this.worldSpace = worldSpace;
        Random random = new Random();
        String[] potentialNames = {
            "Noctori City",
            "Twinleaf Hamlet",
            "Gregory Pass",
            "Emberfall Peak",
            "Spruceheart",
            "Windmill Hills"
        };
        this.name = potentialNames[random.nextInt(potentialNames.length)];
    }

    public WorldSpace getWorldSpace() {
        return worldSpace;
    }

}
