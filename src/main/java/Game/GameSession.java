package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Game.World.World;
import Game.World.WorldSpace;
import Game.World.Direction;
import Game.World.Settlement;

public class GameSession {

    private final Logger log = LoggerFactory.getLogger(GameSession.class);

    public World world;

    public List<PlayerCharacter> players;

    public HashMap<Long, PlayerCharacter> linkedPlayers;

    public GameSession() {
        world = new World(10, 10);
        players = new ArrayList<>();
        linkedPlayers = new HashMap<>();
    }

    public PlayerCharacter createCharacter(long discordId, String characterName) {
        PlayerCharacter newPlayer = new PlayerCharacter(characterName);
        linkedPlayers.put(discordId, newPlayer);

        //Place Character in a Settlement.
        Settlement settlement = world.getRandomSettlement();
        settlement.addPlayer(newPlayer);
        newPlayer.moveTo(settlement);

        return newPlayer;
    }

    public PlayerCharacter getCharacter(long discordId) {
        try {
            return linkedPlayers.get(discordId);
        } catch (Exception e) {
            return null;
        }
    }

    public String moveCharacter(long discordId, Direction dir) {
        PlayerCharacter player = getCharacter(discordId);
        WorldSpace movingFrom = player.getLocation().getWorldSpace();
        WorldSpace movingTo = world.getWorldSpaceOffset(movingFrom, dir);
        if (movingTo == null) {
            return "I can't go " + dir + " that looks to be the edge of the world, I am not ready to commit suicide yet...";
        } else {
            player.getLocation().removePlayer(player);
            player.moveTo(movingTo);
            movingTo.addPlayer(player);
            log.info(player.name + " walked " + dir + " from " + movingFrom.name + " to " + movingTo.name);
            return "I walked " + dir + ".";
        }
    }

}
