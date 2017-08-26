package de.loki.tetramaster;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Loki on 21.08.2017.
 */

public class Slot {

    public static float slotWidth;
    public static float slotHeight;
    public static int slotCount;
    public static float slotScale;
    public Vector2 pos;
    public SlotState state;
    protected Rectangle hitbox;
    private Image img;

    private Card card;


    public Slot(Vector2 pos){
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
        if(card != null) card.draw(batch, pos);
    }

    public void render(Vector2 mousePos){
    }


}
