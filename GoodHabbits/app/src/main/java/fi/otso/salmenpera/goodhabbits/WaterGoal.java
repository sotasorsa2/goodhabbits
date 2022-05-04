package fi.otso.salmenpera.goodhabbits;

public class WaterGoal {
    private int goalToReach;
    private int waterDrankToday;
    private static final WaterGoal ourInstance = new WaterGoal();

    public static WaterGoal getInstance() {
        return ourInstance;
    }

    private WaterGoal() {
        goalToReach = 2000;
        waterDrankToday = 0;
    }

    public int getPercentage() {
        return (int)Math.round(((double)waterDrankToday / (double)goalToReach) * 100);
    }

    public void setGoal(int goal) {
        goalToReach = goal;
    }

    public int getGoal() {
        return goalToReach;
    }

    public void setDrank(int amount) {waterDrankToday = amount;}

    public int getDrank() {return waterDrankToday;}

    public void addWaterDrank(int water) {
        waterDrankToday += water;
    }

    public void removeWaterDrank(int water) {
        if(waterDrankToday - water < 0) {
            waterDrankToday = 0;
        }else {
            waterDrankToday -= water;
        }
    }
}
