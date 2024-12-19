package Game.Item;

public class Item {
    
    public String name;
    public boolean oneHanded;
    public int defensiveValue;
    public int recovery;

    public Item(String name, boolean oneHanded, int defensiveValue, int recovery) {
        this.name = name;
        this.oneHanded = oneHanded;
        this.defensiveValue = defensiveValue;
        this.recovery = recovery;
    }

}
