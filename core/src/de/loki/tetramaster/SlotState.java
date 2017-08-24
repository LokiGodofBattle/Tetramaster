package de.loki.tetramaster;

/**
 * Created by Loki on 21.08.2017.
 */

public enum SlotState {

    EMPTY(0),
    BLOCKED(1),
    FRIENDLY(2),
    OPPOSING(3);

    private int state;

    private SlotState(int state){
        this.state = state;
    }

    public static SlotState getNextSlotState(SlotState state){
        switch (state){
            case EMPTY:
                return BLOCKED;
            case BLOCKED:
                return FRIENDLY;
            case FRIENDLY:
                return OPPOSING;
            case OPPOSING:
                return EMPTY;
            default:
                return EMPTY;
        }
    }

}
