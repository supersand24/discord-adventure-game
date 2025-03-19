package Game.World;

public class Settlement extends Location {

    WorldSpace worldSpace;
    
    public Settlement(WorldSpace worldSpace) {
        this.worldSpace = worldSpace;
        this.name = SettlementNameGenerator.generateName(worldSpace.terrain);
    }

    public WorldSpace getWorldSpace() {
        return worldSpace;
    }

    public String toString() {
        return name + " [ " + worldSpace.xCoord + " , " + worldSpace.yCoord + " ]";
    }

}
