package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Loki on 26.08.2017.
 */

public class CollectionSlot extends Slot {

    private Vector2 arrayPos;

    public CollectionSlot(Vector2 pos, Vector2 arrayPos){
        super(pos);
        this.arrayPos = arrayPos;
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            Gdx.app.log("debug", "APos x: " + arrayPos.x + " y: " + arrayPos.y);
            setSlotState(SlotState.getNextSlotState(state));
        }
    }

}
