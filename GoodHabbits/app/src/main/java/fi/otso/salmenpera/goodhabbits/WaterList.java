package fi.otso.salmenpera.goodhabbits;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Singleton class holding list of WaterGoalForDate objects for previous days' water goals
 * @author Atte Räisänen
 */
public class WaterList {
    private ArrayList<WaterGoalForDate> waterGoalForDates;
    private static final WaterList ourInstance = new WaterList();

    /**
     * Returns reference to our instance
     */
    public static WaterList getInstance() {
        return ourInstance;
    }

    /**
     * Constructor that initializes the ArrayList as a new empty ArrayList
     */
    private WaterList() {
        waterGoalForDates = new ArrayList();
    }

    /**
     * Returns ArrayList of previous days' water goals
     */
    public ArrayList<WaterGoalForDate> getWaterStatuses() {
        return waterGoalForDates;
    }

    /**
     * Setter for ArrayList of water goals
     */
    public void setWaterStatuses(ArrayList<WaterGoalForDate> statuses){
        waterGoalForDates = statuses;
    }


    /**
     * Function to sort water goal list by date
     */
    public void sortWaterList() {
        Collections.sort(waterGoalForDates);
    }

}
