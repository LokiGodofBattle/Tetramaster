package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Loki on 26.08.2017.
 */

public class FieldSlot extends Slot {


    public Vector2 arrayPos;

    public FieldSlot(Vector2 pos, Vector2 arrayPos){
        super(pos);
        this.arrayPos = arrayPos;
        setSlotState(SlotState.EMPTY);
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            Gdx.app.log("debug", "APos x: " + arrayPos.x + " y: " + arrayPos.y);
            setSlotState(SlotState.getNextSlotState(state));
        }
    }

}
