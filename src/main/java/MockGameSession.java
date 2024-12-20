import java.util.Scanner;

import Game.GameSession;
import Game.PlayerCharacter;
import Game.World.Settlement;
import Game.World.WorldSpace;

@SuppressWarnings("unused")
public class MockGameSession {

    static GameSession currentGameSession;

    static Scanner in;

    public static void main(String[] args) {

        currentGameSession = new GameSession();

        currentGameSession.createCharacter(5, "Steve");

        in = new Scanner(System.in);

        awaitInput();

    }

    public static void awaitInput() {
        System.out.println("What would you like to do?");
        PlayerCharacter player = currentGameSession.getCharacter(5);
        switch (in.nextLine()) {
            case "quit" -> System.exit(0);
            case "check-stats" -> System.out.println(player.checkStats());
            case "check-hands" -> System.out.println(player.checkHands());
            case "check-backpack" -> System.out.println(player.checkBackpack());
            case "check-location" -> System.out.println(player.checkLocation());
            default -> System.out.println("Unknown Command, try again.");
        }
        awaitInput();
    }
    
}
