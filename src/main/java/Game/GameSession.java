package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Game.World.World;
import Game.World.WorldSpace;
import Game.World.Direction;

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

    public void moveCharacer(long discordId, Direction dir) {
        PlayerCharacter player = getCharacter(discordId);
        WorldSpace movingFrom = player.getLocation().getWorldSpace();
        WorldSpace movingTo = world.getWorldSpaceOffset(movingFrom, dir);
        if (movingTo == null) {
            System.out.println("I can't go " + dir + " that looks to be the edge of the world, I am not ready to commit suicide yet...");
        } else {
            player.getLocation().removePlayer(player);
            player.moveTo(movingTo);
            movingTo.addPlayer(player);
            System.out.println("I walked " + dir + ".");
        }
    }

}
