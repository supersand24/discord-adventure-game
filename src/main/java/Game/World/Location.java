package Game.World;

import java.util.ArrayList;
import java.util.List;

import Game.PlayerCharacter;

public interface Location {

    List<PlayerCharacter> playersHere = new ArrayList<>();

    default void addPlayer(PlayerCharacter player) {
        playersHere.add(player);
    }

    default void removePlayer(PlayerCharacter player) {
        if (playersHere.contains(player)) {
            playersHere.remove(player);
        } else {
            System.out.println(player.name + " doesn't appear to be at this location.");
        }
    }

    WorldSpace getWorldSpace();
    
}
