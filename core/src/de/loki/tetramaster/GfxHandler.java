package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 21.08.2017.
 */

public class GfxHandler {

    private static Texture blocked;
    private static Texture friendly;
    private static Texture opposing;
    public static Array<Image> arrows;

    public static void initGfx(){
        blocked = new Texture(Gdx.files.internal("blocked.png"));
        friendly = new Texture(Gdx.files.internal("blue.gif"));
        opposing = new Texture(Gdx.files.internal("red.gif"));
        arrows = new Array<Image>();
    }

    public static void initArrowGfx(){
        Image img;

        img = new Image(new Texture(Gdx.files.internal("Arrow_Top.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Top-Right.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Right.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Bottom-Right.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Bottom.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Bottom-Left.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Left.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
        img = new Image(new Texture(Gdx.files.internal("Arrow_Top-Left.png")));
        img.setScale(Slot.slotScale);
        arrows.add(img);
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

    public static Texture getCardTextureByID(int ID){
        int id = ID +1;
        if(id<=9) return new Texture(Gdx.files.internal("00" + id + ".gif"));
        else if(id>9 && id <100) return new Texture(Gdx.files.internal("0" + id + ".gif"));
        else if(id>=100) return new Texture(Gdx.files.internal(id + ".gif"));

        return  new Texture(Gdx.files.internal("empty.png"));
    }

    public static void setAllArrowPositions(Vector2 pos){
        for(int i = 0; i<arrows.size; i++){
            arrows.get(i).setPosition(pos.x, pos.y);
        }
    }

}
