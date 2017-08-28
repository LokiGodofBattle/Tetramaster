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
            if(SaveData.savedCard != null && state == SlotState.EMPTY) {
                setCard(SaveData.savedCard);
                SaveData.savedCard = null;
                setSlotState(SlotState.FRIENDLY);
            }
        }

    }

}
