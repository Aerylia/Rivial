package com.vanerp.slimstampen.slimstampen;

/**
 * Created by Douwe on 9-4-2017.
 */
public class Item {

    private String word;
    private String translation;
    private double activation;

    public Item(String word, String translation) {
        this.word = word;
        this.translation = translation;
        this.activation = 0;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public double getActivation() {
        return activation;
    }

    public void setActivation(double activation) {
        this.activation = activation;
    }

}
