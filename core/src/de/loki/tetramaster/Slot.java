package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    public static float slotScale;
    public static Array<Slot> field;
    public static Array<Slot> playerHand;
    public static Array<Slot> enemyHand;
    public Vector2 arrayPos;
    public Vector2 pos;
    public SlotState state;
    private Rectangle hitbox;
    private Image img;

    private Card card;



    public Slot(Vector2 arrayPos, Vector2 pos){
        this.arrayPos = arrayPos;
        this.pos = pos;
        this.state = SlotState.EMPTY;
        this.hitbox = new Rectangle(pos.x, pos.y, slotWidth, slotHeight);

        img = new Image(GfxHandler.getBackgroundBySlotState(state));
        img.setScale(slotScale);
        img.setPosition(pos.x, pos.y);

    }

    public Slot(Vector2 pos){
        this.arrayPos = new Vector2(-1, -1);
        this.pos = pos;
        this.state = SlotState.BLOCKED;
        this.hitbox = new Rectangle(pos.x, pos.y, slotWidth, slotHeight);

        img = new Image(GfxHandler.getBackgroundBySlotState(state));
        img.setScale(slotScale);
        img.setPosition(pos.x, pos.y);
    }

    public void setCard(Card card){
        this.card = card;
    }


    public void setSlotState(SlotState state){
        this.state = state;
        img = new Image(GfxHandler.getBackgroundBySlotState(state));
        img.setScale(slotScale);
        img.setPosition(pos.x, pos.y);
    }

    public void draw(SpriteBatch batch){
        if(state != SlotState.EMPTY)img.draw(batch, 1);
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            Gdx.app.log("debug", "APos x: " + arrayPos.x + " y: " + arrayPos.y);
            setSlotState(SlotState.getNextSlotState(state));
        }
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

        randomizeBlockedSlots();

    }

    private static void randomizeBlockedSlots(){
        int count = MathUtils.random(0, 5);

        Gdx.app.log("debug", "Count: " + count);

        Array<Integer> pool = new Array<Integer>();

        for(int i = 0; i<field.size; i++){
            pool.add(i);
        }

        for(int i = 0; i<count; i++){
            int x1 = MathUtils.random(0, pool.size-1);
            int x = pool.get(x1);
            pool.removeIndex(x1);

            field.get(x).setSlotState(SlotState.BLOCKED);
        }
    }

    public static void renderAllSlots(Vector2 mousePos){

        for(int i = 0; i<field.size; i++){
            field.get(i).render(mousePos);
        }

        for(int i = 0; i<playerHand.size; i++){
            playerHand.get(i).render(mousePos);
        }

        for(int i = 0; i<enemyHand.size; i++){
            enemyHand.get(i).render(mousePos);
        }

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
