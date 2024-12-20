package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Game.World.World;

public class GameSession {

    public World world;

    public List<PlayerCharacter> players;

    public HashMap<Long, PlayerCharacter> linkedPlayers;

    public GameSession() {
        world = new World(5, 5);
        players = new ArrayList<>();
        linkedPlayers = new HashMap<>();
    }

    public PlayerCharacter createCharacter(long discordId, String characterName) {
        PlayerCharacter newPlayer = new PlayerCharacter(characterName);
        linkedPlayers.put(discordId, newPlayer);

        //Place Character in a Settlement.
        newPlayer.moveTo(world.getRandomSettlement());

        return newPlayer;
    }

    public PlayerCharacter getCharacter(long discordId) {
        try {
            return linkedPlayers.get(discordId);
        } catch (Exception e) {
            return null;
        }
    }

}
