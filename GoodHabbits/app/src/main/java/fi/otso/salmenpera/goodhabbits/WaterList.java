package fi.otso.salmenpera.goodhabbits;

import java.util.ArrayList;
import java.util.Collections;

public class WaterList {
    private ArrayList<WaterGoalForDate> waterGoalForDates;
    private static final WaterList ourInstance = new WaterList();

    public static WaterList getInstance() {
        return ourInstance;
    }

    private WaterList() {
        waterGoalForDates = new ArrayList();
    }

    public ArrayList<WaterGoalForDate> getWaterStatuses() {
        return waterGoalForDates;
    }

    public void setWaterStatuses(ArrayList<WaterGoalForDate> statuses){
        waterGoalForDates = statuses;
    }

    public void sortWaterList() {
        Collections.sort(waterGoalForDates);
    }

}
