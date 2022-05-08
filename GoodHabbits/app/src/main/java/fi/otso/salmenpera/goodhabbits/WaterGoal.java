package fi.otso.salmenpera.goodhabbits;

/**
 * Singleton class that holds user's current water goal information
 * @author Atte Räisänen
 */
public class WaterGoal {
    private int goalToReach;
    private int waterDrankToday;
    private static final WaterGoal ourInstance = new WaterGoal();

    /**
     * Returns the instance of the Singleton class
     */
    public static WaterGoal getInstance() {
        return ourInstance;
    }

    /**
     * Default values for Water Goal
     */
    private WaterGoal() {
        goalToReach = 2000;
        waterDrankToday = 0;
    }


    /**
     * Returns percentage of goal reached so far
     */
    public int getPercentage() {
        return (int)Math.round(((double)waterDrankToday / (double)goalToReach) * 100);
    }

    /**
     * Setter for water goal
     */
    public void setGoal(int goal) {
        goalToReach = goal;
    }

    /**
     * Getter for water goal
     */
    public int getGoal() {
        return goalToReach;
    }

    /**
     * Setter for water amount drank
     */
    public void setDrank(int amount) {waterDrankToday = amount;}


    /**
     * Getter for water amount drank
     */
    public int getDrank() {return waterDrankToday;}

    /**
     * Add more to amount of water drank
     */
    public void addWaterDrank(int water) {
        waterDrankToday += water;
    }
}
