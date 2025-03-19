package Game;

import Game.Item.Item;
import Game.World.Location;
import Game.World.Settlement;
import Game.World.WorldSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerCharacter {

    public String name;

    int maxHitPoints;
    int hitPoints;

    int strength;
    int dexterity;
    int charsima;

    Item rightHand;
    Item leftHand;
    List<Item> backpack = new ArrayList<>();

    Location currentLocation;

    public PlayerCharacter() {
        name = "Unknown Name";

        strength = 10;
        dexterity = 10;
        charsima = 10;

        maxHitPoints = 25;
        hitPoints = maxHitPoints;

        //Inventory
        rightHand = new Item("Iron Sword", true, 2, 0);
        leftHand = new Item("Wooden Shield", true, 5, 0);
        backpack.add(new Item("Minor Potion of Healing", true, 0, 10));
    }

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

    public String checkLocation() {
        StringBuilder str = new StringBuilder();
        if (currentLocation == null) { return "I am currently lost in space and time!"; } else {
            if (currentLocation instanceof Settlement currentSettlement) {
                return "I am in " + currentSettlement.name + ".";
            } else if (currentLocation instanceof WorldSpace currentWorldSpace) {
                str.append("I am standing ").append(currentWorldSpace.getTerrainDescription());
                if (currentWorldSpace.hasSettlement()) {
                    Settlement settlement = currentWorldSpace.getSettlement();
                    str.append("  I am also currently outside of " + settlement.name + ".");
                }
            } else { return "I am somewhere, but I can't tell where."; }
        }
        return str.toString();
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void moveTo(Location location) {
        this.currentLocation = location;
    }

    public String toString() {
        return checkStats();
    }
    
}
