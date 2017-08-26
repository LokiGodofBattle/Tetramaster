package de.loki.tetramaster;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 26.08.2017.
 */

public class SaveData {

    public static Array<Card> collection;

    public static void initData(){
        collection = new Array<Card>();

        for(int i = 0; i<5; i++){
            int id = MathUtils.random(0, 99);
            Card card = new Card(id, CardDataSet.getCardDataSetByID(id));
            card.randomizeArrows();
            collection.add(card);
        }

    }

    public static int getPositionInArrayFromCoordinate(float x, float y){
        return (int) (x*4+y);
    }

}
