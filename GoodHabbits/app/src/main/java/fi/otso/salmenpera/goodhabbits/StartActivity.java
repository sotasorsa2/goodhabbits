package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {
    SharedPreferences tracking;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);

        //storing data to SharedPreferences
        SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);

        //if the tracking is not counting(key) --> redirect to main page
        if(!tracking.contains("counting")){
            Intent intent = new Intent(StartActivity.this, DayActivity.class);
            startActivity(intent);

            //otherwise it will go back from last point
        } else {
            Intent intent = new Intent(StartActivity.this, night_activity.class);
            startActivity(intent);
        }

    }
}