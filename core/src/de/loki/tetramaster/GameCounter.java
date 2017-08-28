package de.loki.tetramaster;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Loki on 28.08.2017.
 */

public class GameCounter {

    private static int friendlys;
    private static int enemys;

    public static void render(){

        friendlys = 0;
        enemys = 0;

        for(int i = 0; i<GameScreen.field.size; i++){
            if(GameScreen.field.get(i).state == SlotState.FRIENDLY) friendlys++;
            if(GameScreen.field.get(i).state == SlotState.OPPOSING) enemys++;
        }
    }

    public static void draw(SpriteBatch batch){

        float xOffset = Main.VIEWPORT_WIDTH/2-(Slot.slotWidth*Slot.slotCount)/2;
        float XOffsetCount = xOffset/2 + xOffset + Slot.slotWidth * Slot.slotCount;

        Main.glyphLayout.setText(Main.countFont, "" + friendlys);
        Main.countFont.setColor(Color.BLUE);
        Main.countFont.draw(batch, "" + friendlys, XOffsetCount-Main.glyphLayout.width/2, Slot.slotWidth+Slot.slotWidth/2-Main.glyphLayout.height/2);

        Main.glyphLayout.setText(Main.countFont, "" + enemys);
        Main.countFont.setColor(Color.ORANGE);
        Main.countFont.draw(batch, "" + enemys, XOffsetCount-Main.glyphLayout.width/2, Slot.slotWidth*2+Slot.slotWidth/2-Main.glyphLayout.height/2);
    }

}
