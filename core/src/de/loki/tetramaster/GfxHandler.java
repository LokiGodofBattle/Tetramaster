package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Loki on 21.08.2017.
 */

public class GfxHandler {

    private static Texture blocked;
    private static Texture friendly;
    private static Texture opposing;

    public static void initGfx(){
        blocked = new Texture(Gdx.files.internal("blocked.png"));
        friendly = new Texture(Gdx.files.internal("blue.gif"));
        opposing = new Texture(Gdx.files.internal("red.gif"));
    }

    public static Texture getBackgroundBySlotState(SlotState state){
        switch (state){
            case BLOCKED:
                return blocked;
            case FRIENDLY:
                return friendly;
            case OPPOSING:
                return opposing;
            default:
                return new Texture(Gdx.files.internal("empty.png"));
        }
    }

}
