package Game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Game.World.World.Terrain;

public class WorldSpace extends Location {

    private final Logger log = LoggerFactory.getLogger(WorldSpace.class);

    public String name;

    public int xCoord;
    public int yCoord;

    Terrain terrain;
    Settlement settlement;
    String feature;

    Map<Direction, Signpost> signposts = new HashMap<>();

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

    public void addSettlementSignpost(Direction direction, Settlement settlement) {
        if (signposts.get(direction) == null) {
            log.info("No Signpost for " + toString() + " " + direction + " creating one now!");
            signposts.put(direction,new Signpost(direction, settlement));
        } else {
            log.info("Existing Signpost found for " + toString() + " adding " + settlement.name + " to " + direction);
            signposts.get(direction).addSettlement(settlement);
        }
    }

    public List<Signpost> getSignposts() {
        return new ArrayList<>(signposts.values());
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
