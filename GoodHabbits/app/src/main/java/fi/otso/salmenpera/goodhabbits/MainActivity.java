package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
/**
 * Main page. Displays buttons to get to each part of the app.
 * @author Hasan
*/
public class MainActivity extends AppCompatActivity {
    /**
     * Get tracking status from SharedPreferences, open night_activity if user was tracking sleep
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sleep Tracking
        //storing data to SharedPreferences
        SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);
        //if the tracking is not counting(key) --> redirect to main page
        if(tracking.contains("counting")) {
            Intent intent = new Intent(MainActivity.this, night_activity.class);
            startActivity(intent);
        }

    }


    /**
     * Open activity for water goal
     */
    public void enterWaterGoal(View v) {
        Intent nextActivity = new Intent(MainActivity.this, WaterActivity.class);
        startActivity(nextActivity);
    }

    /**
     * Open activity for bedtime goal
     */
    public void enterBedTimeGoal(View v) {
        Intent nextActivity = new Intent(MainActivity.this, DayActivity.class);
        startActivity(nextActivity);
    }

    /**
     * Open activity for meditation goal
     */
    public void enterMeditaionGoal(View v) {
        Intent nextActivity3 = new Intent(MainActivity.this, MeditationActitivy.class);
        startActivity(nextActivity3);
    }


}