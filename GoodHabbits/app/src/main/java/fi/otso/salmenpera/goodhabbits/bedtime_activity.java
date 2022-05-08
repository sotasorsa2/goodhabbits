package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
/**
 *Day activity for bed time app.
 * <p>
 *     In the bed activity the user can chose a bad time, Just like an alarm so when the time comes to sleep
 *     the user can go to the main face of the app then can click Sleep Now button to start user's tracking sleep.
 * </p>
 * @author Oumran
 */

public class bedtime_activity extends AppCompatActivity {
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    Button btnStart, btnStop;
    TextView textViewTime, bedTimeGoal;
    CounterClass timer;
    /**
     *On create, initialize variables for buttons and finding them to activate them
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedtime);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        bedTimeGoal = findViewById(R.id.bedTimeGoal);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });

        textAlarmPrompt = (TextView) findViewById(R.id.alarmprompt);
        buttonstartSetDialog = (Button) findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
        });
    }
    /**
     *Open time picker dialog, initialize a customize  the clock Style, Because the UI style list there is no
      valid look, So we made this one.
     *
     */

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                bedtime_activity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Sleep Time");
        timePickerDialog.show();
    }

    //current time format


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        /**
         *On time set, its for initializing the exact time hour by hour and minute by minute and second by second
         * this one has a different time then the system time, It works in milliSeconds, So that we can notice
          that the exact second for sleep has came, then we increase the system time  which works in seconds only,
          The timer will stop counting time when it reaches 0, Because it doesn't work in milliseconds.
         *
         */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timer = new CounterClass((minute * 60 * 1000) + (hourOfDay * 60 * 60 * 1000), 1000);
            long millis = (minute * 60 * 1000) + (hourOfDay * 60 * 60 * 1000);

            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


            bedTimeGoal.setText(hms);

            Date timeNow = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String strDate = formatter.format(timeNow);

            System.out.println("time now " + formatter.format(timeNow));
            System.out.println("hms: " + hms);

            try {
                Date date1 = formatter.parse(hms);
                Date date2 = formatter.parse(strDate);

                //difference in millisecond
                long Millis = date1.getTime() - date2.getTime();

                //converting milliseconds to hour, minute and second
                @SuppressLint("DefaultLocale") String convert = String.format("%02d:%02d:%02d",
                        Millis/(1000*60*60), (Millis%(1000*60*60))/(1000*60), ((Millis%(1000*60*60))%(1000*60))/1000);

                System.out.println("convert: " + convert);
                textViewTime.setText(convert);

            } catch (ParseException e) {
                e.printStackTrace();
            }



        }

    };


    public class CounterClass extends CountDownTimer {
        Date timeNow = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String strDate = formatter.format(timeNow);


        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //@androidx.annotation.RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        /**
         *On tick, initialize the time to increase second by second, And initialize the exact time of the system and increase
          it in the right way.
         *
         */
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            System.out.println("Millis: " + millis);
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            try {
                Date date1 = formatter.parse(hms);
                Date date2 = formatter.parse(strDate);
                if (date1.getTime() >= date2.getTime()) {


                    //difference in millisecond
                    long Millis = date1.getTime() - date2.getTime();

                    //converting milliseconds to hour, minute and second
                    @SuppressLint("DefaultLocale") String convert = String.format("%02d:%02d:%02d",
                            Millis / (1000 * 60 * 60), (Millis % (1000 * 60 * 60)) / (1000 * 60), ((Millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

                    System.out.println("convert: " + convert);
                    textViewTime.setText(convert);
                    //Counter counter = new Counter(0,0,0,0,0);
                    if(convert.length() == 0){

                        onFinish();
                        //textViewTime.setText("00:" + "00:"+ "00");
                    }}else {
                    textAlarmPrompt.setText("Go to bed now!" + "\n Sleep tight!");
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        /**
         *On finish, it is for initializing the time to stop at (00:00:00), So the user knows that hes bed time has came,
         *  And it been called in on tick time done part.
         *
         */
        public void onFinish() {
            if (textViewTime.equals(00)) {
                textViewTime.setText("00:00:00");

            } else {
                textViewTime.setText("convert");
            }
        }


    }
}