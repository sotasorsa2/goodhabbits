package fi.otso.salmenpera.goodhabbits;

import android.util.Log;

/**
 * Stores info on how a user did for a certain day. Info stored: date, amount user drank that day, amount user's goal was and what percentage of goal was reached
 * @author Atte Räisänen
 */
public class WaterGoalForDate implements Comparable<WaterGoalForDate> {
    private int amountDrunk, goalForDay, day, month, year;
    private int percentage;
    /**
     * Basic constructor, takes in year, month, day, amount the water drank and the goal
     */
    public WaterGoalForDate(int year, int month, int day, int amountDrunk, int goalForDay) {
        this.amountDrunk = amountDrunk;
        this.goalForDay = goalForDay;
        this.day = day;
        this.month = month + 1; //Calendarin palauttama MONTH alkaa nollasta
        this.year = year;
        percentage = (int)Math.round(((double)amountDrunk / (double)goalForDay) * 100);
    }


    /**
     * Getter for the amount of water the user has drank
     */
    public int getAmountDrank() {
        return amountDrunk;
    }


    /**
     * Setter for the amount of water the user has drank
     */
    public int getGoalForDay() {
        return goalForDay;
    }


    /**
     * Getter for percentage of water goal reached
     */
    public int getPercentage() {
        return percentage;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    /**
     * Override of toString value for ListView, returns the date and the % of goal reached
     */
    @Override
    public String toString() {
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + "\n" + Integer.toString(percentage) + "% of goal done";
    }


    /**
     * Comparator for making it easy to sort an array for the ListView, sorts by date
     */
    @Override
    public int compareTo(WaterGoalForDate other) {
        if(other.year > this.year) {
            return 1;
        }else if(other.year < this.year) {
            return -1;
        }

        if(other.month > this.month) {
            return 1;
        }else if(other.month < this.month) {
            return -1;
        }

        if(other.day > this.day) {
            return 1;
        }else {
            return -1;
        }
    }
}
