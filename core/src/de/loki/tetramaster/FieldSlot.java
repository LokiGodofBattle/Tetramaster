package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Loki on 26.08.2017.
 */

public class FieldSlot extends Slot {


    public Vector2 arrayPos;
    public static boolean ongoingBattle;
    public boolean battling;
    public int score;

    public FieldSlot(Vector2 pos, Vector2 arrayPos){
        super(pos);
        this.arrayPos = arrayPos;
        setSlotState(SlotState.EMPTY);
        this.score = 0;
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            if(SaveData.savedCard != null && state == SlotState.EMPTY) {
                setCard(SaveData.savedCard);
                SaveData.savedCard = null;
                setSlotState(SlotState.FRIENDLY);
                GameScreen.turn = false;
            }
        }

    }

    public static void battlePhaseOne(FieldSlot attack, FieldSlot defence){

        int attackValue = 0;
        int defenceValue = 0;

        CardDataSet attackData = attack.card.dataSet;
        CardDataSet defenceData = defence.card.dataSet;

        if(attackData.attackType.equals("P")){
            attackValue = attackData.attackValue;
            defenceValue = defenceData.physicDefenceValue;
        } else if(attackData.attackType.equals("M")){
            attackValue = attackData.attackValue;
            defenceValue = defenceData.magicDefenceValue;
        } else if(attackData.attackType.equals("X")){
            attackValue = attackData.attackValue;
            if(defenceData.physicDefenceValue>defenceData.magicDefenceValue) defenceValue = defenceData.magicDefenceValue;
            else defenceValue = defenceData.physicDefenceValue;
        } else if(attackData.attackType.equals("A")){
            int highestAttack = 0;
            if(attackData.attackValue>highestAttack) highestAttack = attackData.attackValue;
            if(attackData.physicDefenceValue>highestAttack) highestAttack = attackData.physicDefenceValue;
            if(attackData.magicDefenceValue>highestAttack) highestAttack = attackData.magicDefenceValue;

            attackValue = highestAttack;

            if(defenceData.physicDefenceValue>defenceData.magicDefenceValue) defenceValue = defenceData.magicDefenceValue;
            else defenceValue = defenceData.physicDefenceValue;
        }

        int attackValueP1 = MathUtils.random(attackValue*16, attackValue*16+15);
        int defenceValueP1 = MathUtils.random(defenceValue*16, defenceValue*16+15);

        int attackValueP2 = MathUtils.random(0, attackValueP1);
        int defenceValueP2 = MathUtils.random(0, defenceValueP1);

        attackValue = attackValueP1 - attackValueP2;
        defenceValue = defenceValueP1 - defenceValueP2;

        if(attackValue == defenceValue) defenceValue++;

        attack.score = attackValue;
        attack.battling = true;
        defence.score = defenceValue;
        defence.battling = true;

        ongoingBattle = true;
    }

    public void draw(SpriteBatch batch){
        if(state != SlotState.EMPTY)img.draw(batch, 1);
        if(card != null && state != SlotState.EMPTY && state != SlotState.BLOCKED) card.draw(batch, pos, battling);
        if(battling){
            String txt = "" + score;
            Main.glyphLayout.setText(Main.font, txt);
            Main.font.draw(batch, txt, pos.x + (Slot.slotWidth-Main.glyphLayout.width)/2, pos.y + 80);
        }
    }

}
