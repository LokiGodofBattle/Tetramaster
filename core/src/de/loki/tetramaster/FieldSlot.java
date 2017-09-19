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
    public boolean lostBattle;
    public int score;

    public FieldSlot(Vector2 pos, Vector2 arrayPos){
        super(pos);
        this.arrayPos = arrayPos;
        setSlotState(SlotState.EMPTY);
        this.score = 0;
        this.lostBattle = false;
    }

    public void render(Vector2 mousePos){
        if(hitbox.contains(new Vector2(mousePos.x, mousePos.y)) && Gdx.input.justTouched()){
            if(SaveData.savedCard != null && state == SlotState.EMPTY) {
                playCard();
                battle();
            }
        }

    }

    private void playCard(){
        setCard(SaveData.savedCard);
        SaveData.savedCard = null;
        setSlotState(SlotState.FRIENDLY);

        GameScreen.turn = false;
    }

    private void battle(){

        int battles = 0;

        boolean[] results = checkForBattles(arrayPos.x, arrayPos.y);

        for(int i = 0; i<results.length; i++){
            if(results[i]) battles++;
        }

        if(battles == 1){
            for(int i = 0; i<results.length; i++){
                Vector2 neighbour = getArrayPosFromDirection(i);
                if(results[i]){
                    battlePhaseOne(this, (FieldSlot) GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(arrayPos.x + neighbour.x, arrayPos.y + neighbour.y)));
                }
            }
        }

        if(battles == 0)flag();

    }

    private void flag(){
        boolean[] results = checkForFlag(arrayPos.x, arrayPos.y);

        for(int i = 0; i<results.length; i++){
            Vector2 neighbour = getArrayPosFromDirection(i);

            if(results[i]) GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(arrayPos.x + neighbour.x, arrayPos.y + neighbour.y)).setSlotState(SlotState.FRIENDLY);

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

        if(attackValue>defenceValue) defence.lostBattle = true;
        else attack.lostBattle = true;

        ongoingBattle = true;
    }

    public static void battlePhaseTwo(){
        for(int i = 0; i<GameScreen.field.size; i++){
            FieldSlot field = GameScreen.field.get(i);

            field.battling = false;
            if(field.lostBattle && field.state == SlotState.FRIENDLY) field.setSlotState(SlotState.OPPOSING);
            else if(field.lostBattle && field.state == SlotState.OPPOSING) field.setSlotState(SlotState.FRIENDLY);
        }

        ongoingBattle = false;
    }

    public static boolean[] checkForBattles(float x, float y){

        FieldSlot homefield = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x, y));

        boolean[] arrows = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x, y)).card.arrows;

        boolean[] results = new boolean[8];

        for(int i = 0; i<results.length; i++){
            results[i] = false;
        }

        FieldSlot field = null;

        if(y+1<4){
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 0, y + 1));
            if(field.card != null && field.state != homefield.state)results[0] = field.card.arrows[4] && arrows[0];
        }
        if(x+1<4 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[1] = field.card.arrows[5] && arrows[1];
        }
        if(x+1<4){
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 1, y + 0));
            if(field.card != null && field.state != homefield.state)results[2] = field.card.arrows[6] && arrows[2];
        }
        if(x-1>-1 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[3] = field.card.arrows[7] && arrows[3];
        }
        if(y-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 0, y - 1));
            if (field.card != null && field.state != homefield.state) results[4] = field.card.arrows[0] && arrows[4];
        }
        if(x-1>-1 && y-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y - 1));
            if (field.card != null && field.state != homefield.state) results[5] = field.card.arrows[1] && arrows[5];
        }
        if(x-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 0));
            if (field.card != null && field.state != homefield.state) results[6] = field.card.arrows[2] && arrows[6];
        }
        if(x-1>-1 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[7] = field.card.arrows[3] && arrows[7];
        }

        return results;
    }

    public static boolean[] checkForFlag(float x, float y){

        FieldSlot homefield = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x, y));

        boolean[] arrows = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x, y)).card.arrows;

        boolean[] results = new boolean[8];

        for(int i = 0; i<results.length; i++){
            results[i] = false;
        }

        FieldSlot field = null;

        if(y+1<4){
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 0, y + 1));
            if(field.card != null && field.state != homefield.state)results[0] = arrows[0];
        }
        if(x+1<4 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[1] = arrows[1];
        }
        if(x+1<4){
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 1, y + 0));
            if(field.card != null && field.state != homefield.state)results[2] = arrows[2];
        }
        if(x-1>-1 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[3] = arrows[3];
        }
        if(y-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x + 0, y - 1));
            if (field.card != null && field.state != homefield.state) results[4] = arrows[4];
        }
        if(x-1>-1 && y-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y - 1));
            if (field.card != null && field.state != homefield.state) results[5] = arrows[5];
        }
        if(x-1>-1) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 0));
            if (field.card != null && field.state != homefield.state) results[6] = arrows[6];
        }
        if(x-1>-1 && y+1<4) {
            field = GameScreen.field.get(SaveData.getPositionInArrayFromCoordinate(x - 1, y + 1));
            if (field.card != null && field.state != homefield.state) results[7] = arrows[7];
        }

        return results;
    }

    private Vector2 getArrayPosFromDirection(int direction){
        switch (direction){
            case 0:
                return new Vector2(0, 1);
            case 1:
                return new Vector2(1, 1);
            case 2:
                return new Vector2(1, 0);
            case 3:
                return new Vector2(-1, 1);
            case 4:
                return new Vector2(0, -1);
            case 5:
                return new Vector2(-1, -1);
            case 6:
                return new Vector2(-1, 0);
            case 7:
                return new Vector2(-1, 1);
            default:
                return new Vector2(0, 0);
        }
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
