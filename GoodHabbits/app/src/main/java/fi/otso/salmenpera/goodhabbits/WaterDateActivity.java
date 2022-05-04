package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WaterDateActivity extends AppCompatActivity {
    TextView goalText, dateText, summaryText;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_date);
        Bundle b = getIntent().getExtras();
        goalText = findViewById(R.id.dailyGoalWaterText);
        dateText = findViewById(R.id.dateText);
        img = findViewById(R.id.waterImage);
        summaryText = findViewById(R.id.summaryText);
        goalText.setText(Integer.toString(b.getInt("drank")) + "ml/" + Integer.toString(b.getInt("goal")) + "ml");
        dateText.setText(Integer.toString(b.getInt("year") )+ "-" + Integer.toString(b.getInt("month")) + "-" + Integer.toString(b.getInt("day")));
        if(b.getInt("percentage") >= 100) {
            summaryText.setTextColor(Color.GREEN);
            summaryText.setText("GOAL MET FOR DAY");
            img.setImageResource(R.drawable.water_full);
        }else {
            summaryText.setTextColor(Color.RED);
            summaryText.setText("GOAL NOT MET FOR DAY");
        }
    }
}