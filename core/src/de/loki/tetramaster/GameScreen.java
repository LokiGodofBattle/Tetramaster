package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 25.08.2017.
 */

public class GameScreen implements Screen {

    private Main mainClass;
    public Image background;
    public static Array<FieldSlot> field;
    public static Array<Slot> playerHand;
    public static Array<Slot> enemyHand;
    public static int handSize;
    public static boolean turn; //true = friendly, false = enemy
    public static float enemyTurnDelay;
    public static float delayTimer;
    public static boolean rdyToPick;
    private static int selectedCard = 0;

    public GameScreen(Main mainClass){
        this.mainClass = mainClass;

        background = new Image(new Texture(Gdx.files.internal("BG.png")));
        background.setScaleY(Main.VIEWPORT_HEIGHT/background.getHeight());
        background.setScaleX(Slot.slotWidth*4/background.getWidth());
        background.setPosition(Main.VIEWPORT_WIDTH/2-(background.getWidth()*background.getScaleX())/2, 0);

        turn = true;
        enemyTurnDelay = 1f;
        delayTimer = 0f;
        rdyToPick = false;

        initSlots();
        HandSlot.initHand();
        HandSlot.initEnemyHand();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        Main.camera.unproject(mouse);

        if(FieldSlot.ongoingBattle && delayTimer<enemyTurnDelay){
            delayTimer += Gdx.graphics.getDeltaTime();
        }
        else if(FieldSlot.ongoingBattle){
            delayTimer = 0;
            FieldSlot.battlePhaseTwo();
        } else if(rdyToPick){
            for(int i = 0; i<field.size; i++){
                field.get(i).render(new Vector2(mouse.x, mouse.y));
            }
        }
        else if(turn){
            delayTimer = 0;
            renderAllSlots(new Vector2(mouse.x, mouse.y));
        }
        else if(delayTimer<enemyTurnDelay){
            delayTimer += Gdx.graphics.getDeltaTime();
        } else {
            delayTimer = 0;

            enemyTurn();

            turn = true;
        }

        GameCounter.render();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainClass.batch.setProjectionMatrix(Main.camera.combined);
        mainClass.batch.begin();
        background.draw(mainClass.batch, 1);
        drawAllSlots(mainClass.batch);
        GameCounter.draw(mainClass.batch);
        mainClass.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static void enemyTurn(){

//        int id;
//        while (true){
//            id = MathUtils.random(0, field.size-1);
//            if(field.get(id).card == null){
//                field.get(id).setCard(SaveData.enemyHand.get(MathUtils.random(0, SaveData.enemyHand.size-1)));
//                field.get(id).setSlotState(SlotState.OPPOSING);
//                break;
//            }
//        }

        int[][] fieldMatrix = new int[4][4];

        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                int score = 0;

                boolean[] battleResults = FieldSlot.checkForBattles(i, j, SaveData.enemyHand.get(selectedCard));
                boolean[] flagResults;

                int battles = 0;
                for(int k = 0; k<battleResults.length; k++){
                    if(battleResults[k]) battles++;
                }

                score += battles;

                flagResults = FieldSlot.checkForFlag(i, j, SaveData.enemyHand.get(selectedCard));

                int flags = 0;
                for(int k = 0; k<flagResults.length; k++){
                    if(flagResults[k] && !battleResults[k]) flags++;
                }

                score += flags*2;

                fieldMatrix[i][j] = score;
            }
        }

        int highestValue = Integer.MIN_VALUE;
        Vector2 pos = new Vector2(0, 0);

        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                if(fieldMatrix[i][j]>highestValue && field.get(SaveData.getPositionInArrayFromCoordinate(i, j)).state == SlotState.EMPTY){
                    highestValue = fieldMatrix[i][j];
                    pos.x = i;
                    pos.y = j;
                }
            }
        }

        Gdx.app.log("debug", "1");
        field.get(SaveData.getPositionInArrayFromCoordinate(pos.x, pos.y)).setCard(SaveData.enemyHand.get(selectedCard));
        field.get(SaveData.getPositionInArrayFromCoordinate(pos.x, pos.y)).setSlotState(SlotState.OPPOSING);
        field.get(SaveData.getPositionInArrayFromCoordinate(pos.x, pos.y)).battle();

        enemyHand.get(selectedCard).setSlotState(SlotState.EMPTY);

        selectedCard++;
    }

    public static void initSlots(){

        handSize = 5;

        field = new Array<FieldSlot>();
        playerHand = new Array<Slot>();
        enemyHand = new Array<Slot>();

        float xOffset = Main.VIEWPORT_WIDTH/2-(Slot.slotWidth*Slot.slotCount)/2;

        //Playfield
        for(int i = 0; i<Slot.slotCount; i++){
            for(int j = 0; j<Slot.slotCount; j++){
                field.add(new FieldSlot(new Vector2(xOffset+i*Slot.slotWidth, j*Slot.slotHeight), new Vector2(i, j)));
            }
        }

        float yOffset = (Main.VIEWPORT_HEIGHT-Slot.slotHeight*3)/4;
        float xOffsetHand = (xOffset-Slot.slotWidth*2)/3;
        float yOffsetSecondRow = (Main.VIEWPORT_HEIGHT-Slot.slotHeight*2)/3;

        //PlayerHand
        for (int i = 0; i<handSize; i++){
            if(i%2 == 0) playerHand.add(new HandSlot(new Vector2(xOffsetHand, (i/2+1)*yOffset+i/2*Slot.slotHeight)));
            else playerHand.add(new HandSlot(new Vector2(xOffsetHand*2+Slot.slotWidth, (i/2+1)*yOffsetSecondRow+i/2*Slot.slotHeight)));
        }

        float xOffsetEnemyHand = (xOffset-Slot.slotWidth)/2 + xOffset + Slot.slotWidth * Slot.slotCount;

        //EnemyHand
        for(int i = 0; i<handSize; i++){
            enemyHand.add(new HandSlot(new Vector2(xOffsetEnemyHand, Main.VIEWPORT_HEIGHT-Slot.slotHeight-Slot.slotHeight/4*i)));
        }

        randomizeBlockedSlots();

    }

    private static void randomizeBlockedSlots(){
        int count = MathUtils.random(0, 5);

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
