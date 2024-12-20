package Game.World;

import Game.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class Settlement {

    public String name;
    WorldSpace worldSpace;

    List<PlayerCharacter> playersHere = new ArrayList<>();
    
    public Settlement(WorldSpace worldSpace) {
        this.name = "Unnamed Settlement";
        this.worldSpace = worldSpace;
    }

}
