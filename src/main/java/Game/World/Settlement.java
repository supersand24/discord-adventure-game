package Game.World;

import Game.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settlement extends Location {

    public String name;
    WorldSpace worldSpace;

    List<PlayerCharacter> playersHere = new ArrayList<>();
    
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

}
