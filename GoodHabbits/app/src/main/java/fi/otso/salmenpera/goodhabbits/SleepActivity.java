package fi.otso.salmenpera.goodhabbits;
//import Statement
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Sleep Activity
 * @author Hasan
 */

public class SleepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        //SharedPreferences
        SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);
    }
}