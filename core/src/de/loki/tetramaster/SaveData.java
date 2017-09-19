package de.loki.tetramaster;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Loki on 26.08.2017.
 */

public class SaveData {

    public static Array<Card> collection;
    public static Array<Card> selected;
    public static Card savedCard;
    public static Array<Card> hand;
    public static Array<Card> enemyHand;

    public static void initData(){
        collection = new Array<Card>();
        hand = new Array<Card>();
        selected = new Array<Card>();
        enemyHand = new Array<Card>();

        for(int i = 0; i<5; i++){
            int id = MathUtils.random(0, 99);
            Card card = new Card(id, CardDataSet.getCardDataSetByID(id));
            card.randomizeArrows();
            collection.add(card);
        }

        for(int i = 0; i<5; i++){
            int id = MathUtils.random(0, 99);
            Card card = new Card(id, CardDataSet.getCardDataSetByID(id));
            card.randomizeArrows();
            enemyHand.add(card);
        }

    }

    public static int getPositionInArrayFromCoordinate(float x, float y){
        return (int) (x*4+y);
    }

}
