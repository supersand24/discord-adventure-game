package Game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Game.PlayerCharacter;

public abstract class Location {

    private final Logger log = LoggerFactory.getLogger(Location.class);

    public String name = "Nameless Location";

    protected List<PlayerCharacter> playersHere = new ArrayList<>();
    public Map<Direction, Settlement> signposts = new HashMap<>();

    // Add a player to the location
    public void addPlayer(PlayerCharacter player) {
        playersHere.add(player);
    }

    // Remove a player from the location
    public void removePlayer(PlayerCharacter player) {
        if (playersHere.contains(player)) {
            playersHere.remove(player);
        } else {
            log.error(player.name + " doesn't appear to be at " + name + ".");
        }
    }

    // Abstract method to be implemented by subclasses
    public abstract WorldSpace getWorldSpace();

    // Getter for name (optional, if needed)
    public String getName() {
        return name;
    }

    // Setter for name (optional, if needed)
    public void setName(String name) {
        this.name = name;
    }
}
