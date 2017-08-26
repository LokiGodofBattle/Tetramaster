package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Loki on 21.08.2017.
 */

public class Card {

    public int cardID;
    public boolean[] arrows;
    public CardDataSet dataSet;
    private Image img;

    public Card(int cardID, CardDataSet dataSet){
        this.cardID = cardID;
        this.arrows = new boolean[8];
        this.dataSet = dataSet;
        this.img = new Image(GfxHandler.getCardTextureByID(cardID));
        this.img.setScale(Slot.slotScale);
    }

    public void randomizeArrows(){
        for(int i = 0; i<8; i++){
            arrows[i] = MathUtils.randomBoolean();
        }
    }

    public void draw(SpriteBatch batch, Vector2 pos){
        drawArrows(batch, pos);
        img.setPosition(pos.x, pos.y);
        img.draw(batch, 1);
    }

    private void drawArrows(SpriteBatch batch, Vector2 pos){

        GfxHandler.setAllArrowPositions(pos);

        for(int i = 0; i<arrows.length; i++){
            if(arrows[i])GfxHandler.arrows.get(i).draw(batch, 1);
        }
    }

}
