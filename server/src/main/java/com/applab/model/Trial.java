package com.vanerp.slimstampen.slimstampen;

/**
 * Class object that will be returned when asking the SlimStampen model for a new trial
 */

public class Trial {

    private TrialType type;
    private Item item;

    public boolean isTestTrial() {
        return (type == TrialType.TEST);
    }

    public boolean isStudyTrial() {
        return (type == TrialType.STUDY);
    }

    public TrialType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public Trial(TrialType type, Item item) {
        this.type = type;
        this.item = item;
    }

    public enum TrialType {
        STUDY, TEST
    }

}
