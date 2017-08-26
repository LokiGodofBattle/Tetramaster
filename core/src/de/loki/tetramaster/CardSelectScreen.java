package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 25.08.2017.
 */

public class CardSelectScreen implements Screen {

    private Main mainClass;
    private Array<Slot> collection;
    private int collectionSizeX;

    public CardSelectScreen(Main mainClass){
        this.mainClass = mainClass;
        collection = new Array<Slot>();

        initSlots();
        setCardsInSlots();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        Main.camera.unproject(mouse);

        renderCollectionSlots(new Vector2(mouse.x, mouse.y));

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainClass.batch.setProjectionMatrix(Main.camera.combined);
        mainClass.batch.begin();
        drawCollectionSlots(mainClass.batch);
        mainClass.batch.end();
    }

    private void setCardsInSlots(){
        for(int i = 0; i<SaveData.collection.size; i++){
            int x = i % collectionSizeX;
            int y = Slot.slotCount-1 - i/collectionSizeX;
            collection.get(SaveData.getPositionInArrayFromCoordinate(x, y)).setCard(SaveData.collection.get(i));
            collection.get(SaveData.getPositionInArrayFromCoordinate(x, y)).setSlotState(SlotState.FRIENDLY);
        }
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

    private void initSlots(){
        collectionSizeX = (int)(Main.VIEWPORT_WIDTH/Slot.slotWidth);

        float bufferX = (Main.VIEWPORT_WIDTH-collectionSizeX*Slot.slotWidth)/2;

        for(int i = 0; i<collectionSizeX; i++){
            for(int j = 0; j<Slot.slotCount; j++){
                Slot slot = new CollectionSlot(new Vector2(i*Slot.slotWidth+bufferX, j*Slot.slotHeight), new Vector2(i, j));
                collection.add(slot);
            }
        }
    }

    private void renderCollectionSlots(Vector2 mousePos){
        for(int i = 0; i<collection.size; i++){
            collection.get(i).render(mousePos);
        }
    }

    private void drawCollectionSlots(SpriteBatch batch){
        for(int i = 0; i<collection.size; i++){
            collection.get(i).draw(batch);
        }
    }

}
