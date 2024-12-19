package Game;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    static World world = new World(5,5);

    static List<PlayerCharacter> players = new ArrayList<>();
    
    public static void main(String[] args) {

        players.add(new PlayerCharacter("Bob"));

        System.out.println(players.get(0).toString());
        
    }

}
