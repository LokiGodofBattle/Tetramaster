package de.loki.tetramaster;

/**
 * Created by Loki on 21.08.2017.
 */

public class CardDataSet {

    public int attackValue;
    public String attackType;
    public int magicDefenceValue;
    public int physicDefenceValue;

    public CardDataSet(int attackValue, String attackType, int magicDefenceValue, int physicDefenceValue){
        this.attackValue = attackValue;
        this.attackType = attackType;
        this.magicDefenceValue = magicDefenceValue;
        this.physicDefenceValue = physicDefenceValue;
    }

}
