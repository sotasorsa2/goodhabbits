package fi.otso.salmenpera.goodhabbits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class night_activity extends AppCompatActivity {
    Button stopTracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        stopTracking = findViewById(R.id.stpTrackBtn);

        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(night_activity.this, stat_activity.class);
                //PutExtra

                startActivity(intent);

            }
        });
    }
}