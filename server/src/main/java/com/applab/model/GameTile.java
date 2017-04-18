package com.applab.model;

import java.io.Serializable;

/**
 * Created by arian on 18-4-2017.
 */

public class GameTile implements Serializable{

    private String word;
    private String translation;
    private int id;
    private boolean ownedByBlue;
    private boolean ownedByRed;
    private boolean ownedByYellow;

    public GameTile(String word, String translation, int id){
        this.word = word;
        this.translation = translation;
        this.ownedByBlue = false;
        this.ownedByRed = false;
        this.ownedByYellow = false;
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public boolean isOwnedByBlue() {
        return ownedByBlue;
    }

    public boolean isOwnedByRed() {
        return ownedByRed;
    }

    public boolean isOwnedByYellow() {
        return ownedByYellow;
    }

    public void setOwnedByBlue(boolean ownedByBlue) {
        this.ownedByBlue = ownedByBlue;
    }

    public void setOwnedByRed(boolean ownedByRed) {
        this.ownedByRed = ownedByRed;
    }

    public void setOwnedByYellow(boolean ownedByYellow) {
        this.ownedByYellow = ownedByYellow;
    }
}
