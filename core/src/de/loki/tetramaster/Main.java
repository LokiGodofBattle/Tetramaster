package de.loki.tetramaster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {

	public static OrthographicCamera camera;
	public Viewport viewport;
	public final static int VIEWPORT_WIDTH = 2560;
	public static float VIEWPORT_HEIGHT;
	public static float scale;
	private static float aspect_ratio;
	public static BitmapFont font;
	public static BitmapFont countFont;
	private static FreeTypeFontGenerator ftfGenerator;
	private static FreeTypeFontGenerator.FreeTypeFontParameter ftfParameter;
	public static GlyphLayout glyphLayout;
	public SpriteBatch batch;
	private GameScreen gameScreen;
	private CardSelectScreen cardSelectScreen;
	
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

		ftfGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Titania.ttf"));
		ftfParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		ftfParameter.size = 75;
		ftfParameter.color = Color.WHITE;
		ftfParameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";

		font = ftfGenerator.generateFont(ftfParameter);

		ftfParameter.size = 300;

		countFont = ftfGenerator.generateFont(ftfParameter);

		glyphLayout = new GlyphLayout();

		batch = new SpriteBatch();
		GfxHandler.initGfx();
		CardDataSet.initData();

		Slot.slotCount = 4;
		Slot.slotHeight = Main.VIEWPORT_HEIGHT/Slot.slotCount;
		Slot.slotScale = Slot.slotHeight/GfxHandler.getBackgroundBySlotState(SlotState.BLOCKED).getHeight();
		Slot.slotWidth = GfxHandler.getBackgroundBySlotState(SlotState.BLOCKED).getWidth()*Slot.slotScale;

		GfxHandler.initArrowGfx();
		SaveData.initData();

		cardSelectScreen = new CardSelectScreen(this);
		gameScreen = new GameScreen(this);
		this.setScreen(cardSelectScreen);
	}

	@Override
	public void render () {
		super.render();
		if(SaveData.selected.size == 5) this.setScreen(gameScreen);
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
