package com.applab.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 */

public class WordList implements Serializable{

    private ArrayList<Item> wordList;

    /**
     * Create a new empty WordList
     */
    public WordList() {
        wordList = new ArrayList<>();
    }

    /**
     * Load a word list from a file
     * @param fileName The file name of the word list
     *                 Each line should be formatted like: word = translation
     * @return the updated WordList
     */
    public static WordList loadFromFile(String fileName) {
        WordList wordList = new WordList();
        // TODO fill wordlist from file filename
        return wordList;
    }

    /**
     * Load a word list from a resource
     * @param resourceId The resource name of the word list
     *                 Each line should be formatted like: word = translation
     * @return the updated WordList
     */
/*    public WordList loadFromResource(Context ctx, int resourceId) {
        InputStream inputStream = ctx.getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while (( line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
*/
    /**
     * Add an item
     * @param word The word the user knows
     * @param translation The the foreign translation of the word
     * @return the updated WordList
     */
    public WordList addItem(String word, String translation) {
        wordList.add( new Item(word, translation) );
        return this;
    }

    public WordList removeItem(int i) {
        wordList.remove(i);
        return this;
    }

    public Item getItem(int i) {
        return wordList.get(i);
    }

    public Item getRandomItem() {
        Random random = new Random();
        int i = random.nextInt( wordList.size() );
        return wordList.get(i);
    }

    public int getItemCount() {
        return wordList.size();
    }

    public ArrayList<Item> getWordList() {
        return wordList;
    }

    /**
     * Parse a line containing a word and translation
     * Store them into the dictionary
     * @param line A string, e.g.: "word = translation"
     */
    private void parseLine(String line) {
        String[] pair = line.split("=");
        String word = pair[0].trim();
        String translation = pair[1].trim();
        addItem(word, translation);
    }
}