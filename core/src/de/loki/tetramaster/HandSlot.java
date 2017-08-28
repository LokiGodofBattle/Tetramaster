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
            if(SaveData.savedCard == null && card != null){
                SaveData.savedCard = card;
                card = null;
                setSlotState(SlotState.EMPTY);
            }
        }
    }

    public static void initHand(){
        for(int i = 0; i<GameScreen.playerHand.size; i++){
            SaveData.hand.add(SaveData.collection.get(i));
        }

        for(int i = 0; i<GameScreen.playerHand.size; i++){
            GameScreen.playerHand.get(i).setCard(SaveData.hand.get(i));
            GameScreen.playerHand.get(i).setSlotState(SlotState.FRIENDLY);
        }

    }

}
