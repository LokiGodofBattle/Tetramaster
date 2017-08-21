package de.loki.tetramaster;

/**
 * Created by Loki on 21.08.2017.
 */

public class Card {

    public int cardID;
    public boolean[] arrows;
    public CardDataSet dataSet;

    public Card(int cardID, boolean[] arrows, CardDataSet dataSet){
        this.cardID = cardID;
        this.arrows = arrows;
        this.dataSet = dataSet;
    }

}
