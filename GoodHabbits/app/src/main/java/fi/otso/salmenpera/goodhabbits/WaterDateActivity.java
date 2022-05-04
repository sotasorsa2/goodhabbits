package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WaterDateActivity extends AppCompatActivity {
    TextView goalText, dateText, summaryText, percentageText;
    ImageView img;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_date);
        Bundle b = getIntent().getExtras();
        goalText = findViewById(R.id.dailyGoalWaterText);
        dateText = findViewById(R.id.dateText);
        img = findViewById(R.id.waterImage);
        summaryText = findViewById(R.id.summaryText);
        percentageText = findViewById(R.id.percentageText);
        progressBar = findViewById(R.id.waterProgressBar);
        goalText.setText(Integer.toString(b.getInt("drank")) + "ml/" + Integer.toString(b.getInt("goal")) + "ml");
        dateText.setText(Integer.toString(b.getInt("year") )+ "-" + Integer.toString(b.getInt("month")) + "-" + Integer.toString(b.getInt("day")));
        percentageText.setText(b.getInt("percentage") + "%");
        progressBar.setProgress(b.getInt("percentage"));
        if(b.getInt("percentage") >= 100) {
            summaryText.setTextColor(Color.GREEN);
            summaryText.setText("GOAL MET FOR DAY");
            img.setImageResource(R.drawable.water_full);
        }else {
            summaryText.setTextColor(Color.RED);
            summaryText.setText("GOAL NOT MET FOR DAY");
        }
    }

    public void goBack(View v) {
        Intent nextActivity = new Intent(WaterDateActivity.this, WaterListActivity.class);
        startActivity(nextActivity);
    }
}