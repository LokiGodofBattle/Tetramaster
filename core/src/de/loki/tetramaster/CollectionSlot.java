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
            if(card != null && state == SlotState.FRIENDLY){
                SaveData.selected.add(card);
                setSlotState(SlotState.OPPOSING);
            } else if(card != null && state == SlotState.OPPOSING){
                SaveData.selected.removeValue(card, true);
                setSlotState(SlotState.FRIENDLY);
            }
        }
    }

}
