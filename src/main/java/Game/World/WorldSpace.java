package Game.World;

import Game.World.World.Terrain;

public class WorldSpace {

    Terrain terrain;
    Settlement settlement;

    public WorldSpace(Terrain terrain) {
        this.terrain = terrain;
    }

    public Settlement createSettlement() {
        settlement = new Settlement();
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
