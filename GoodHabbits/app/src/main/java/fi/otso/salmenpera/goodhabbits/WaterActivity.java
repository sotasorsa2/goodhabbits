package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class WaterActivity extends AppCompatActivity {
    private TextView waterGoalView;
    private TextView percentageText;
    private WaterGoal wg = WaterGoal.getInstance();
    private ImageView waterImageView;
    private EditText waterAmountText;
    private ProgressBar progressBar;
    private Date currentDate;
    private Calendar cal;
    private int lastSeenDay, lastSeenMonth, lastSeenYear, waterImageId, waterGoal;
    private ArrayList<String> drankWatersList = new ArrayList(); //List of previous days and how the user did


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        currentDate = new Date();
        cal = new GregorianCalendar();
        cal.setTime(currentDate); //Initialize a new GregorianCalendar to record today's date in a more clear format

        //Initializing layout references
        waterGoalView = findViewById(R.id.dailyGoalWaterText);
        waterGoalView.setText(Integer.toString(wg.getDrank()) + "/" + Integer.toString(wg.getGoal()));
        waterImageView = findViewById(R.id.waterImage);
        waterAmountText = findViewById(R.id.editTextWaterAmount);
        progressBar = findViewById(R.id.waterProgressBar);
        percentageText = findViewById(R.id.percentageText);

        //Load SharedPreferences into variables
        handleDataLoad();

        if(!lastSeenToday()) {
            handleWaterListSave();
        }
        updateGoalText();

        //Initialize WaterList with our saved data for previous days' water goals
        WaterList.getInstance().setWaterStatuses(convertDrankWatersListToWaterStatus());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Function call", "onPause() called");
        handleDataSave();
    }

    //Does today's date match last recorded date?
    public Boolean lastSeenToday() {
        if(lastSeenDay != 0 && lastSeenMonth != 0 && lastSeenYear != 0) {
            if(Integer.parseInt(String.valueOf(cal.get(Calendar.YEAR))) > lastSeenYear || Integer.parseInt(String.valueOf(cal.get(Calendar.MONTH))) > lastSeenMonth || Integer.parseInt(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))) > lastSeenDay) {
                return false;
            }
        }
        return true;
    }

    public void add250ml(View v) {
        if(!waterOverMax(250)) {
            wg.addWaterDrank(250);
            updateGoalText();
        }
    }

    public void add500ml(View v) {
        if(!waterOverMax(500)) {
            wg.addWaterDrank(500);
            updateGoalText();
        }
    }

    public void add1000ml(View v) {
        if(!waterOverMax(1000)) {
            wg.addWaterDrank(1000);
            updateGoalText();
        }
    }


    public void addCustomWater(View v) {
        if(waterAmountText.getText().length() > 0) { //Check to prevent crash when EditText field is empty
            int waterAmountToAdd = Integer.parseInt(String.valueOf(waterAmountText.getText()));
            if(waterAmountToAdd > 0 && waterAmountToAdd < 10000) {
                wg.addWaterDrank(Integer.parseInt(String.valueOf(waterAmountText.getText())));
                updateGoalText();
            }
        }
    }

    //Does the water amount we're trying to add make drank water go above 10000?
    private Boolean waterOverMax(int add) {
        if(wg.getDrank() + add >= 10000) {
            return true;
        }else {
            return false;
        }
    }

    //Save variable values into SharedPreferences to be loaded next time
    public void handleDataSave() {
        SharedPreferences prefPut = getSharedPreferences("waterDrank", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("drankWater", wg.getDrank());
        prefEditor.putInt("waterGoal", waterGoal);
        prefEditor.putInt("waterId", waterImageId);
        prefEditor.putInt("lastYear", Integer.parseInt(String.valueOf(cal.get(Calendar.YEAR))));
        prefEditor.putInt("lastMonth", Integer.parseInt(String.valueOf(cal.get(Calendar.MONTH))));
        prefEditor.putInt("lastDay", Integer.parseInt(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))));
        prefEditor.commit();
    }

    //Add results for the day to drank waters list and update SharedPreferences with the new list
    public void handleWaterListSave() {
        Log.d("function call", "handleWaterListSave()");
        String s = String.valueOf(lastSeenYear) + "," + Integer.toString(lastSeenMonth) + "," + String.valueOf(lastSeenDay) + "," + Integer.toString(wg.getDrank()) + "," + Integer.toString(wg.getGoal());
        drankWatersList.add(s);
        SharedPreferences prefPut = getSharedPreferences("waterDrank", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        Set<String> set = new HashSet<>(drankWatersList); //Making a HashSet out of drankWatersList so we can store it into preferences
        prefEditor.putStringSet("waterList", set);
        wg.setDrank(0);
        prefEditor.putInt("drankWater", wg.getDrank());
        prefEditor.commit();
    }

    //Load SharedPreferences into variables
    public void handleDataLoad() {
        Log.d("function call", "handleDataLoad()");
        SharedPreferences prefGet = getSharedPreferences("waterDrank", Activity.MODE_PRIVATE);
        waterGoal = prefGet.getInt("waterGoal", wg.getGoal());
        wg.setDrank(prefGet.getInt("drankWater", wg.getDrank()));
        wg.setGoal(waterGoal);
        waterImageId = prefGet.getInt("waterId", R.drawable.water_half);
        Set<String> hs = new HashSet<>();
        hs = prefGet.getStringSet("waterList", null); //Get the HashSet of drankWatersList
        if(hs != null) {
            drankWatersList = new ArrayList<>(hs); //Convert HashSet back into String ArrayList
        }
        lastSeenYear = prefGet.getInt("lastYear", 0);
        lastSeenMonth = prefGet.getInt("lastMonth", 0);
        lastSeenDay = prefGet.getInt("lastDay", 0);
    }

    public void updateGoalText() {
        waterGoalView.setText(Integer.toString(wg.getDrank()) + "ml/" + Integer.toString(wg.getGoal()) + "ml");
        progressBar.setProgress(wg.getPercentage());
        percentageText.setText(wg.getPercentage() + "%");
        updateWaterImage();
    }

    public void updateWaterImage() {
        if(wg.getDrank() >= wg.getGoal()) {
            waterImageId = R.drawable.water_full;
        }else {
            waterImageId = R.drawable.water_half;
        }
        waterImageView.setImageResource(waterImageId);
    }

    //For debugging, clears all SharedPreferences
    public void resetPrefs() {
        Log.d("function call", "WaterActivity.resetPrefs()");
        Log.d("INFO", "CLEARING ALL SHARED PREFERENCES");
        SharedPreferences prefGet = getSharedPreferences("waterDrank", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefGet.edit();
        prefEditor.clear();
        prefEditor.commit();
    }

    //Converts drankWatersList from String type to WaterStatus type
    public ArrayList<WaterGoalForDate> convertDrankWatersListToWaterStatus() {
        Log.d("function call", "convertDrankWatersListToWaterStatus()");
        ArrayList result = new ArrayList(); //Initialize Array to return
        ArrayList<Integer> ints = new ArrayList(); //Initialize Array to store Integers taken from drankWatersList
        for(int i = 0; i < drankWatersList.size(); i++) {
            for(String s : drankWatersList.get(i).split(",")) { //Take each section separated by "," from each entry in the array
                ints.add(Integer.parseInt(s)); //Turn String vaue into Integer and add it to list
            }
        }
        for(int i = 0; i < ints.size() / 5; i++) {
            int multiplier = i * 5;
            if(i == 0) {
                //Create a new WaterStatus using values from the Integers we just extracted
                WaterGoalForDate ws = new WaterGoalForDate(ints.get(i), ints.get(i+1), ints.get(i+2), ints.get(i+3), ints.get(i+4));
                //Add new WaterStatus object to return list
                result.add(ws);
            }else {
                //Create a new WaterStatus using values from the Integers we just extracted using a bit of math to get the correct indexes
                WaterGoalForDate ws = new WaterGoalForDate(ints.get(multiplier), ints.get(multiplier+1), ints.get(multiplier+2), ints.get(multiplier+3), ints.get(multiplier+4));
                //Add new WaterStatus object to return list
                result.add(ws);
            }


        }
        //Return extracted list of WaterStatuses
        return result;
    }

    public void goBack(View v) {
        Intent nextActivity = new Intent(WaterActivity.this, MainActivity.class);
        startActivity(nextActivity);
    }

    //Load activity for previous water goals
    public void loadWaterListActivity(View v) {
        Intent nextActivity = new Intent(WaterActivity.this, WaterListActivity.class);
        startActivity(nextActivity);
    }
}