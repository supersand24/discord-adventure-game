package Game.World;

import java.util.ArrayList;
import java.util.List;

public class Signpost {

    List<Settlement> settlements = new ArrayList<>();
    Direction direction;

    public Signpost(Direction dir, Settlement settlement) {
        this.direction = dir;
        settlements.add(settlement);
    }

    public void addSettlement(Settlement settlement) {
        settlements.add(settlement);
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public Direction getDirection() {
        return direction;
    }
    
}
