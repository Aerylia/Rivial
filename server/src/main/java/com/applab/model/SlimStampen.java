package com.applab.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * References
 * --------------------------
 * [1]
 */

public class SlimStampen {

    private Random random;
    private WordList wl;
    private Map<Item, ArrayList<Double>> d; // decay
    private Map<Item, ArrayList<Double>> a; // alpha
    private Map<Item, ArrayList<Double>> t; // time
    private Map<Item, ArrayList<Double>> A; // activation
    private ArrayList<Item> novel; // Keep track of novel items
    private Trial activeTrial;


    // Lookahead time in seconds to try to select items before they are forgotten
    private final double n = 15;

    // Decay scale parameter
    private final double c = 1;

    // Reaction Time (RT) scale parameter
    private final double F = 1;

    // Fixed time for non-memory related processes e.g. reading and typing
    private final double f = 1000;

    // Retrieval threshold for activation value, usually between -0.5 and -0 (Van Woudenberg, 2008; Van Thiel, 2010).
    // This gives the maximum spacing, while retaining the benefit of the testing effect (Carrier and Pashler, 1992).
    private final double threshold = -0.5;

    public SlimStampen(WordList wordList) {
        random = new Random();
        wl = wordList;
        d = new HashMap<>();
        a = new HashMap<>();
        t = new HashMap<>();
        A = new HashMap<>();
        novel = new ArrayList();

        // Prepare lists
        for ( Item item : wl.getWordList() ) {
            novel.add(item);
            d.put(item, new ArrayList());
            a.put(item, new ArrayList());
            A.put(item, new ArrayList());
            t.put(item, new ArrayList());
        }

    }

    /**
     * Determines the next trial for the current SlimStampen model
     * @return a Trial object
     */
    public Trial nextTrial() {
        Trial.TrialType type;
        Item item;
        double T = getTime();

        // Pick item with the lowest activation in n seconds
        Item lowest = wl.getItem(0);
        for (int i = 1; i < wl.getItemCount(); i++) {
            item = wl.getItem(i);
            if ( getActivation(item, T+n*1000) < getActivation(lowest, T+n*1000) )
                lowest = item;
        }

        // Return if it's below the threshold
        if (lowest.getActivation() < threshold) {
            type = Trial.TrialType.TEST;
            item = lowest;
            t.get(item).add(T);
        } else {
            // Select random novel item if they are still available
            type = Trial.TrialType.STUDY;
            item = (novel.size() > 0) ? novel.get( random.nextInt(novel.size()) ) : lowest;
        }

        // Present selected item
        activeTrial = new Trial( type, item );
        return activeTrial;
    }

    /**
     * Call this to when the test trial is presented to the user
     */
    public void startRecall() {
        Item i = activeTrial.getItem();
        t.get(i).add(getTime());
    }

    /**
     * Check the translation for the current trial.
     * If it was a test trial, update the model.
     * @param translation
     * @return <code>true</code> if the correct translation was given
     */
    public boolean giveTranslation(String translation) {
        boolean correct = translation.equals(activeTrial.getItem().getTranslation());
        if ( activeTrial.isStudyTrial() ) {
            if (correct) {
                novel.remove(activeTrial.getItem());
            }
        } else {
            updateModel();
        }
        return correct;
    }

    public void updateModel() {
        Item i = activeTrial.getItem();
        int n = t.get(i).size(); // Number of previous encounters
        int k = n-1; // Index of last encounter

        double T = getTime(); // The current time
        double RT_max = 1.5 * F * Math.exp(threshold) + f; //F * Math.exp(1.5*threshold) + f; // For personal fixed time
        double RT_obs = Math.min( RT_max, T - t.get(i).get(k) );
        double RT_est = Math.min( RT_max, F * Math.exp(A.get(i).get(k)) + f );


        // Observed activation of last encounter
        double A_obs = -Math.log((RT_obs - f)/F);

        // Activation not contributing to the previous encounter
        double m = 0;
        for (int j = 1; j < n-1; j++) {
            m += Math.pow( T - t.get(i).get(j), -d.get(i).get(j));
        }

        // Calculate the decay of the previous encounter
        d.get(i).add(
                -Math.log( Math.exp(A_obs)-m) /
                        Math.log( T - t.get(i).get(n) )
        );

        // Calculate the alpha from the decay and the activation of the previous encounter
        a.get(i).add(
                d.get(i).get(k) - c * Math.exp(A.get(i).get(k))
        );

        // Compute the error between observed and calculated reaction times
        // for both a_fit and a_new
        // Use a binary search
        // use only last 4 trials to fit the alpha
    }

    /**
     * Base level activation equation
     * @param i an item from the word list
     * @param T a time point
     * @return activation for an item at time t
     */
    public double getActivation(Item i, double T) {
        double m = 0;
        int n = t.get(i).size();

        for (int j = 1; j < n; j++) {
            if ( t.get(i).get(j) >= T ) break; // t_j<t
            m += Math.pow( T - t.get(i).get(j), -d.get(i).get(j));
        }

        return Math.log(m);
    }

    /**
     * Use this to save the learning session for a certain user and certain word list
     * @param filename
     * @return
     */
    public boolean saveSessionToFile(String filename) {
        // TODO: save alphas?
        // TODO: session end (see page 28/79)
        return true;
    }

    /**
     * Use this to retrieve the learning session for a certain user and certain word list
     * @param filename
     * @return
     */
    public boolean loadSessionFromFile(String filename) {
        // TODO
        return true;
    }


    public double getTime() {
        Calendar c = Calendar.getInstance();
        return (double)c.getTimeInMillis();
    }

}
