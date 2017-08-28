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
        img.setPosition(pos.x, pos.y);
        img.draw(batch, 1);
        drawArrows(batch, pos);
        String txt = "" + Integer.toHexString(dataSet.attackValue).toUpperCase() + dataSet.attackType +
                Integer.toHexString(dataSet.physicDefenceValue).toUpperCase() + Integer.toHexString(dataSet.magicDefenceValue).toUpperCase();
        Main.glyphLayout.setText(Main.font, txt);
        Main.font.draw(batch, txt, pos.x + (Slot.slotWidth-Main.glyphLayout.width)/2, pos.y + 80);
    }

    private void drawArrows(SpriteBatch batch, Vector2 pos){

        GfxHandler.setAllArrowPositions(pos);

        for(int i = 0; i<arrows.length; i++){
            if(arrows[i])GfxHandler.arrows.get(i).draw(batch, 1);
        }
    }

}
