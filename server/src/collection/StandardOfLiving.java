package collection;

import java.io.Serializable;

public enum StandardOfLiving implements Serializable {
    ULTRA_HIGH(2),
    LOW(1),
    NIGHTMARE(0);

    private final int level;
    private static final long serialVersionUID=1010L;

    StandardOfLiving(int level){
        this.level=level;
    }

    public int getLevel() {
        return level;
    }
}
