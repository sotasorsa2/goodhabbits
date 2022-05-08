package fi.otso.salmenpera.goodhabbits;
//import statement
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main activity for sleep analysis, in this page user can see how well the user slept and when he went to sleep and when he woke up.
 * @author Hasan
 */

public class stat_activity extends AppCompatActivity {
    Button backToHome;
    SharedPreferences tracking;
    SharedPreferences.Editor ed;
    TextView dateStat, wentToSleep, wakeUp, percentageAnalys;
    ProgressBar progressBar;


    /**
     *On create, getting the status and initUI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        percentageAnalys = findViewById(R.id.progress_circularPercentage);
        progressBar = findViewById(R.id.progress_circular);
        initUI();
        getStatus();
        backToHome = findViewById(R.id.backtohome);

        //redirect button to Home page
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent backToMainActivity = new Intent(stat_activity.this, MainActivity.class);
                startActivity(backToMainActivity);
            }
        });
    }

    /**
     *getStatus gets the current time, stored date. setting up the progress bar and if statement that the percentage should not go over 100%.
     */
    private void getStatus(){
        //SharedPreferences
        SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);
        ed = tracking.edit();
        //Current date
        Date currentDate = new Date();
        dateStat.setText(DateFormat.getDateInstance().format(currentDate));
        //date and time format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        SimpleDateFormat displayTime = new SimpleDateFormat("HH:mm:ss");
        try {
            Date storeDate = formatter.parse(tracking.getString("counting", formatter.format(currentDate)));
            System.out.println("storeDate: " + storeDate);
            //difference between the current time (wake up time) and store time (went to sleep)
            double different = currentDate.getTime() - storeDate.getTime();
            System.out.println("different in millisecond: " + different);

            //change millisecond to minute
            double minutes = different/60000;
            System.out.println("difference in minutes between went to sleep and wakeup: " + minutes + " minutes and milliseconds");

            //Percent max is 100 and best duration time for a user to sleep is recommended which is 8 hours 8 * 60 = 480 minutes
            int percent = (int) Math.round(minutes / 480 * 100);
            System.out.println("Percantage from 8 hours: " + percent + "%");

            //setting the progress bar
            progressBar.setProgress(percent);

            //setting the maximum percent to 100 percent and minimum to 0
            if(percent > 100){
                int maximumPercent = 100;
                percentageAnalys.setText( maximumPercent + "%");
            } else if (percent < 0) {
                int minimumPercent = 0;
                percentageAnalys.setText( minimumPercent + "%");
            } else {
                percentageAnalys.setText(percent + "%");
            }

            //set went to sleep
            wentToSleep.setText(displayTime.format(storeDate));
            wakeUp.setText(displayTime.format(currentDate));


            ed.remove("counting");
            ed.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *initUI, initialize variables
     */

    public void initUI(){
        dateStat = findViewById(R.id.datestat);
        wentToSleep = findViewById(R.id.wenttosleep);
        wakeUp = findViewById(R.id.wakeup);

    }
}