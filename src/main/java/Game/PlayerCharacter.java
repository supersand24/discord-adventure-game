package Game;

import java.util.Random;

public class PlayerCharacter {

    String name;

    int maxHitPoints;
    int hitPoints;

    int strength;
    int dexterity;
    int charsima;

    public PlayerCharacter(String name) {
        this.name = name;

        Random random = new Random();
        strength = random.nextInt(10) + 1;
        dexterity = random.nextInt(10) + 1;
        charsima = random.nextInt(10) + 1;

        maxHitPoints = random.nextInt(dexterity) + strength + 4;
        hitPoints = maxHitPoints;
    }

    public String toString() {
        return name + " " + hitPoints + "/" + maxHitPoints + "\nStr: " + strength + "\nDex: " + dexterity + "\nCha: " + charsima;
    }
    
}
