package Game.World;

import Game.World.World.Terrain;

public class WorldSpace {

    public int xCoord;
    public int yCoord;

    Terrain terrain;
    Settlement settlement;

    public WorldSpace(Terrain terrain, int x, int y) {
        this.terrain = terrain;
        this.xCoord = x;
        this.yCoord = y;
    }

    public Settlement createSettlement() {
        settlement = new Settlement(this);
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
    
}
