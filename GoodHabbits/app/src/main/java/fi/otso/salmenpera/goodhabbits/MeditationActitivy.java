package fi.otso.salmenpera.goodhabbits;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

/**
    * This is the meditation activity for the application.
* The activity is started when the user clicks the meditation button in the main activity.
* The activity is closed when the user clicks the stop button.
* The activity is closed when the user clicks the back button.
*@author otso salmenperä
*/
public class MeditationActitivy extends AppCompatActivity {

    TextView textView, txt2, over, medTimes;
    long startTime = 0;
    int times = 0;
    Button start;
/**
 * This is the onCreate method for the activity.
 * initializes variables and views and buttons.
 * hides textView and txt2.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_actitivy);

        textView = findViewById(R.id.ins);

        start = findViewById(R.id.start);
        txt2 = findViewById(R.id.count);
        txt2.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        over = findViewById(R.id.over);
        medTimes = findViewById(R.id.TIMES);
        // get times from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        times = sharedPreferences.getInt("times", 0);
        medTimes.setText("Times: " + times);


    }




/**
 * This is method is called when the user clicks the start button
 * starts the timer and shows the textView and txt2.
 * is main logic for the activity.
 */

   Handler timeHandler = new Handler();

    Runnable timeRunnable = new Runnable() {


        @Override

        public void run() {


            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;




            seconds = seconds % 60;
            txt2.setText(String.format("%d:%02d", minutes, seconds));


                if( startTime > 0 && seconds < 4) {
                    textView.setText("Breathe in");
                }else if(startTime > 4 && seconds < 11) {
                    textView.setText("hold");

                }else if(startTime > 11 && seconds < 19) {
                    textView.setText("Breathe out");
                }else if(startTime > 19 && seconds < 23) {
                    textView.setText("breathe in");
                }else if(startTime > 23 && seconds < 30) {
                    textView.setText("hold");
                }else if(startTime > 30 && seconds < 38) {
                    textView.setText("Breathe out");
                }else if(startTime > 38 && seconds < 42) {
                    textView.setText("breathe in");
                }else if(startTime > 42 && seconds < 49) {
                    textView.setText("hold");
                }else if(startTime > 49 && seconds < 57) {
                    textView.setText("Breathe out");
                }else if(startTime > 57 && seconds < 61) {

                    medTimes.setText(String.valueOf(times));
                    medTimes.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    over.setVisibility(View.VISIBLE);
                }









                timeHandler.postDelayed(this, 1000);


            };;

    };





    /**
     * This method stops main meditation logic.

     */

    public void onPause() {
        super.onPause();
        timeHandler.removeCallbacks(timeRunnable);
    }
    /**
     This method starts main meditation logic.
     */

    public void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis();
        timeHandler.postDelayed(timeRunnable, 0);
    }




/**
* handles button clicks
* start button starts the timer and shows the textView and txt2.
* stop button callables the onPause method and stops the timer.
* Back button goes back to the main menu.

 */
    public void onClick(View v) {
        if(v.getId() == R.id.start) {

            textView.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            over.setVisibility(View.INVISIBLE);
            medTimes.setVisibility(View.INVISIBLE);
            startTime = System.currentTimeMillis();
            timeHandler.postDelayed(timeRunnable, 0);
            times++;
        }
        if(v.getId() == R.id.stop) {
            onPause();
        }
        if(v.getId() == R.id.Back ) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }



    }

    /**save times to shared preferences*/
    public void saveTimes(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("times", times);
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    /**load times from shared preferences*/
    public void loadTimes(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        times = sharedPreferences.getInt("times", 0);
        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
    }

}