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

public class bedtime_activity extends AppCompatActivity {
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    Button btnStart, btnStop;
    TextView textViewTime, bedTimeGoal;
    CounterClass timer;

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
        public void onFinish() {
            if (textViewTime.equals(00)) {
                textViewTime.setText("00:00:00");

            } else {
                textViewTime.setText("convert");
            }
        }


    }
}