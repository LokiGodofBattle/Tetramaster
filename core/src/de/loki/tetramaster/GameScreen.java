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
    public static Array<Slot> field;
    public static Array<Slot> playerHand;
    public static Array<Slot> enemyHand;
    public static int handSize;

    public GameScreen(Main mainClass){
        this.mainClass = mainClass;

        background = new Image(new Texture(Gdx.files.internal("BG.png")));
        background.setScaleY(Main.VIEWPORT_HEIGHT/background.getHeight());
        background.setScaleX(Slot.slotWidth*4/background.getWidth());
        background.setPosition(Main.VIEWPORT_WIDTH/2-(background.getWidth()*background.getScaleX())/2, 0);

        initSlots();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        Main.camera.unproject(mouse);

        renderAllSlots(new Vector2(mouse.x, mouse.y));

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainClass.batch.setProjectionMatrix(Main.camera.combined);
        mainClass.batch.begin();
        background.draw(mainClass.batch, 1);
        drawAllSlots(mainClass.batch);
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

    public static void initSlots(){

        handSize = 5;

        field = new Array<Slot>();
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

        float xOffsetEnemyHand = xOffset + Slot.slotWidth*4;

        //EnemyHand
        for(int i = 0; i<handSize; i++){
            if(i%2 == 0) playerHand.add(new HandSlot(new Vector2(xOffsetHand+xOffsetEnemyHand, (i/2+1)*yOffset+i/2*Slot.slotHeight)));
            else playerHand.add(new HandSlot(new Vector2(xOffsetEnemyHand+xOffsetHand*2+Slot.slotWidth, (i/2+1)*yOffsetSecondRow+i/2*Slot.slotHeight)));
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
