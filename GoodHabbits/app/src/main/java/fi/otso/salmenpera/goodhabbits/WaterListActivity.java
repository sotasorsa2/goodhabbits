package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WaterListActivity extends AppCompatActivity {
    ListView waterListView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_list);
        waterListView = findViewById(R.id.drankWatersListView);
        waterListView.setBackgroundColor(Color.argb(100, 40, 57, 189));
        WaterList.getInstance().sortWaterList(); //Sort WaterList just in case
        populateWaterList();
    }

    public void populateWaterList() {
        waterListView.setAdapter(new ArrayAdapter<WaterGoalForDate>(
        this,
        R.layout.water_item_layout,
        WaterList.getInstance().getWaterStatuses())
        );

        waterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(WaterListActivity.this, WaterDateActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("year", WaterList.getInstance().getWaterStatuses().get(i).getYear());
                extras.putInt("month", WaterList.getInstance().getWaterStatuses().get(i).getMonth());
                extras.putInt("day", WaterList.getInstance().getWaterStatuses().get(i).getDay());
                extras.putInt("drank", WaterList.getInstance().getWaterStatuses().get(i).getAmountDrank());
                extras.putInt("goal", WaterList.getInstance().getWaterStatuses().get(i).getGoalForDay());
                extras.putInt("percentage", WaterList.getInstance().getWaterStatuses().get(i).getPercentage());
                nextActivity.putExtras(extras);
                startActivity(nextActivity);
            }
        });
    }

    public void goBack(View v) {
        Intent nextActivity = new Intent(WaterListActivity.this, WaterActivity.class);
        startActivity(nextActivity);
    }
}