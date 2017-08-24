package de.loki.tetramaster;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends ApplicationAdapter {

	private static OrthographicCamera camera;
	private Viewport viewport;
	public final static int VIEWPORT_WIDTH = 2560;
	public static float VIEWPORT_HEIGHT;
	public static float scale;
	private static float aspect_ratio;
	private SpriteBatch batch;
	private Image background;
	
	@Override
	public void create () {

		//Ausrechnen des Größenverhältnisses des Geräts
		aspect_ratio = Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();

		camera = new OrthographicCamera();

		//FitViewport damit es auf allen Geräten gleich aussieht
		viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * aspect_ratio, camera);
		viewport.apply();

		//Kamera zentrieren
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_WIDTH * aspect_ratio / 2f, 0);

		VIEWPORT_HEIGHT = VIEWPORT_WIDTH * aspect_ratio;

		scale = VIEWPORT_WIDTH/Gdx.graphics.getWidth();

		batch = new SpriteBatch();

		GfxHandler.initGfx();

		Slot.slotCount = 4;
		Slot.slotHeight = VIEWPORT_HEIGHT/Slot.slotCount;
		Slot.slotScale = Slot.slotHeight/GfxHandler.getBackgroundBySlotState(SlotState.BLOCKED).getHeight();
		Slot.slotWidth = GfxHandler.getBackgroundBySlotState(SlotState.BLOCKED).getWidth()*Slot.slotScale;

		background = new Image(new Texture(Gdx.files.internal("BG.png")));
		background.setScaleY(VIEWPORT_HEIGHT/background.getHeight());
		background.setScaleX(Slot.slotWidth*4/background.getWidth());
		background.setPosition(VIEWPORT_WIDTH/2-(background.getWidth()*background.getScaleX())/2, 0);

		Slot.initSlots();

	}

	@Override
	public void render () {

		Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

		camera.unproject(mouse);

		Slot.renderAllSlots(new Vector2(mouse.x, mouse.y));

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch, 1);
		Slot.drawAllSlots(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_WIDTH * aspect_ratio / 2f, 0);
	}
}
