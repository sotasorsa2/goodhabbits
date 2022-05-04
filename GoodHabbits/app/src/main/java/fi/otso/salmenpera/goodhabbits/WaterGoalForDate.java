package fi.otso.salmenpera.goodhabbits;

import android.util.Log;

public class WaterGoalForDate implements Comparable<WaterGoalForDate> {
    private int amountDrunk, goalForDay, day, month, year;
    private int percentage;
    public WaterGoalForDate(int year, int month, int day, int amountDrunk, int goalForDay) {
        this.amountDrunk = amountDrunk;
        this.goalForDay = goalForDay;
        this.day = day;
        this.month = month + 1; //Calendarin palauttama MONTH alkaa nollasta
        this.year = year;
        percentage = (int)Math.round(((double)amountDrunk / (double)goalForDay) * 100);
        Log.d("test", Double.toString(percentage));
    }

    public int getAmountDrank() {
        return amountDrunk;
    }

    public int getGoalForDay() {
        return goalForDay;
    }

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

    @Override
    public String toString() {
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + "\n" + Integer.toString(percentage) + "% of goal done";
    }

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
