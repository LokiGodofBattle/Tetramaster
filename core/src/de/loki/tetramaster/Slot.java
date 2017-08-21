package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 21.08.2017.
 */

public class Slot {

    public static float slotWidth;
    public static float slotHeight;
    public static int slotCount;
    public static int handSize;
    public static Array<Slot> field;
    public static Array<Slot> playerHand;
    public static Array<Slot> enemyHand;
    public Vector2 arrayPos;
    public Vector2 pos;
    public SlotState state;
    private Rectangle hitbox;

    private Card card;



    public Slot(Vector2 arrayPos, Vector2 pos){
        this.arrayPos = arrayPos;
        this.pos = pos;
        this.state = SlotState.BLOCKED;
        this.hitbox = new Rectangle(pos.x, pos.y, slotWidth, slotHeight);
    }

    public Slot(Vector2 pos){
        this.arrayPos = null;
        this.pos = pos;
        this.state = SlotState.BLOCKED;
        this.hitbox = new Rectangle(pos.x, pos.y, slotWidth, slotHeight);
    }

    public void setCard(Card card){
        this.card = card;
    }

    public static void initSlots(){

        handSize = 5;

        field = new Array<Slot>();
        playerHand = new Array<Slot>();
        enemyHand = new Array<Slot>();

        float xOffset = Main.VIEWPORT_WIDTH/2-(slotWidth*slotCount)/2;

        //Playfield
        for(int i = 0; i<slotCount; i++){
            for(int j = 0; j<slotCount; j++){
                field.add(new Slot(new Vector2(i, j), new Vector2(xOffset+i*slotWidth, j*slotHeight)));
            }
        }

        float yOffset = (Main.VIEWPORT_HEIGHT-slotHeight*3)/4;
        float xOffsetHand = (xOffset-slotWidth*2)/3;
        float yOffsetSecondRow = (Main.VIEWPORT_HEIGHT-slotHeight*2)/3;

        //PlayerHand
        for (int i = 0; i<handSize; i++){
            if(i%2 == 0) playerHand.add(new Slot(new Vector2(xOffsetHand, (i/2+1)*yOffset+i/2*slotHeight)));
            else playerHand.add(new Slot(new Vector2(xOffsetHand*2+slotWidth, (i/2+1)*yOffsetSecondRow+i/2*slotHeight)));
        }

        float xOffsetEnemyHand = xOffset + slotWidth*4;

        //EnemyHand
        for(int i = 0; i<handSize; i++){
            if(i%2 == 0) playerHand.add(new Slot(new Vector2(xOffsetHand+xOffsetEnemyHand, (i/2+1)*yOffset+i/2*slotHeight)));
            else playerHand.add(new Slot(new Vector2(xOffsetEnemyHand+xOffsetHand*2+slotWidth, (i/2+1)*yOffsetSecondRow+i/2*slotHeight)));
        }

    }

    public void draw(SpriteBatch batch){
        Image img = new Image(GfxHandler.getBackgroundBySlotState(state));
        img.setScale(slotHeight/img.getHeight());
        img.setPosition(pos.x, pos.y);
        img.draw(batch, 1);
    }

    public static void drawAllSlots(SpriteBatch batch){

        for(int i = 0; i<field.size; i++){
            field.get(i).draw(batch);
        }

        for(int i = 0; i<playerHand.size; i++){
            playerHand.get(i).draw(batch);
        }

        for(int i = 0; i<enemyHand.size; i++){
            enemyHand.get(i).draw(batch);
        }

    }

}
