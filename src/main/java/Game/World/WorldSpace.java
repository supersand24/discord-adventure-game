package Game.World;

import Game.World.World.Terrain;

public class WorldSpace extends Location{

    public String name;

    public int xCoord;
    public int yCoord;

    Terrain terrain;
    Settlement settlement;
    String feature;

    public WorldSpace(Terrain terrain, int x, int y) {
        this.terrain = terrain;
        this.xCoord = x;
        this.yCoord = y;
        this.name = terrain.name();
    }

    public WorldSpace getWorldSpace() {
        return this;
    }

    public Settlement createSettlement() {
        settlement = new Settlement(this);
        return settlement;
    }

    public boolean hasSettlement() {
        return settlement != null;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public String getTerrainSymbol() {
        switch (terrain) {
            case Plains -> { return "P"; }
            case Forest -> { return "F"; }
            case River -> { return "R"; }
            case Mountain -> { return "M"; }
            default -> { return "?"; }
        }
    }

    public String getTerrainDescription() {
        switch (terrain) {
            case Plains -> { return "in an open expanse of grassy plains."; }
            case Forest -> { return "in a dense forest filled with tall trees."; }
            case River -> { return "next to a flowing river with sparkling water."; }
            case Mountain -> { return "on a towering mountain with rugged peaks."; }
            default -> { return "in an unknown terrain."; }
        }
    }

    public String toString() {
        return "WorldSpace X:" + xCoord + " Y:" + yCoord;
    }
    
}
