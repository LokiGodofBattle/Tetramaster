package de.loki.tetramaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Loki on 21.08.2017.
 */

public class CardDataSet {

    public int attackValue;
    public String attackType;
    public int magicDefenceValue;
    public int physicDefenceValue;
    private static FileHandle data;
    private static String[] cardData;

    public CardDataSet(int attackValue, String attackType, int magicDefenceValue, int physicDefenceValue){
        this.attackValue = attackValue;
        this.attackType = attackType;
        this.magicDefenceValue = magicDefenceValue;
        this.physicDefenceValue = physicDefenceValue;
    }

    public static void initData(){
        data = Gdx.files.internal("CardData.txt");

         cardData = data.readString().split("\n");
    }

    private static CardDataSet getCardDataSetWithString(String data){
        char[] split = data.toCharArray();

        int av = Integer.parseInt("" + split[0], 16);
        String at = "" + split[1];
        int mdv = Integer.parseInt("" + split[2], 16);
        int pdv = Integer.parseInt("" + split[3], 16);

        return new CardDataSet(av, at, mdv, pdv);
    }

    private void randomize(){
        attackValue += getChance() - 2;
        magicDefenceValue += getChance() - 2;
        physicDefenceValue += getChance() - 2;

        if(attackValue<0) attackValue = 0;
        if(magicDefenceValue<0) magicDefenceValue = 0;
        if(physicDefenceValue<0) physicDefenceValue = 0;
    }

    private static int getChance(){
        int rdm = MathUtils.random(0, 100);

        if(rdm<=60) return 0;
        else if(rdm>60 && rdm<90) return 1;
        else if(rdm>=90) return 2;

        return -1;
    }

    public static CardDataSet getCardDataSetByID(int id){
        CardDataSet set = getCardDataSetWithString(cardData[id]);
        set.randomize();
        return set;
    }

}
