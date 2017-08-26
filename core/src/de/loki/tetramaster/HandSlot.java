package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Loki on 26.08.2017.
 */

public class HandSlot extends Slot {

    public HandSlot(Vector2 pos){
        super(pos);
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            setSlotState(SlotState.getNextSlotState(state));
        }
    }

}
