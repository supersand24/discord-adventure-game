package Game;

import Game.Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerCharacter {

    String name;

    int maxHitPoints;
    int hitPoints;

    int strength;
    int dexterity;
    int charsima;

    Item rightHand;
    Item leftHand;
    List<Item> backpack = new ArrayList<>();

    public PlayerCharacter(String name) {
        this.name = name;

        Random random = new Random();
        strength = random.nextInt(10) + 1;
        dexterity = random.nextInt(10) + 1;
        charsima = random.nextInt(10) + 1;

        maxHitPoints = random.nextInt(dexterity) + strength + 4;
        hitPoints = maxHitPoints;

        //Inventory
        rightHand = new Item("Iron Sword", true, 2, 0);
        leftHand = new Item("Wooden Shield", true, 5, 0);
        backpack.add(new Item("Minor Potion of Healing", true, 0, 10));
    }

    public String checkStats() {
        return name + " " + hitPoints + "/" + maxHitPoints + "\nStr: " + strength + "\nDex: " + dexterity + "\nCha: " + charsima;
    }

    public String checkHands() {
        return "I am holding an " + rightHand.name + " in my right hand and a " + leftHand.name + " in my left.";
    }

    public String checkBackpack() {
        if (backpack == null) {
            return "I seem to be missing my backpack.";
        } else {
            StringBuilder str = new StringBuilder();
            str.append("In my backpack I see...");
            for (Item item : backpack) {
                str.append("\nA ").append(item.name);
            }
            return str.toString();
        }
    }

    public String toString() {
        return checkStats();
    }
    
}
