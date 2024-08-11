package com.example.disaster;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.disaster.Emergencysos.SmsActivity;
import com.example.disaster.Post.Post;

public class Dashboard extends AppCompatActivity {
    private CardView emergencyCard, guideCard, scheduleCard, askCard, healthCard, locationCard, seizureEventCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initializing CardViews
        emergencyCard = findViewById(R.id.weatherCard);
        guideCard = findViewById(R.id.incidentCard);
        scheduleCard = findViewById(R.id.sosCard);
        askCard = findViewById(R.id.findCard);
        healthCard = findViewById(R.id.announcementCard);
        locationCard = findViewById(R.id.guideCard);
        seizureEventCard = findViewById(R.id.askCard);

        // Setting onClickListeners for each card
        emergencyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Weather Alert Clicked", Toast.LENGTH_SHORT).show();
                // Intent to another activity, e.g., WeatherAlertActivity
                // startActivity(new Intent(DashboardActivity.this, WeatherAlertActivity.class));
                vibrate();
            }
        });

        guideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Incident Report Clicked", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(Dashboard.this, Post.class));
                vibrate();
            }
        });

        scheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "SOS Button Clicked", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(Dashboard.this, SmsActivity.class));
                vibrate();
            }
        });

        askCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Resource Finder Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(DashboardActivity.this, ResourceFinderActivity.class));
                vibrate();
            }
        });

        healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Important Announcement Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(DashboardActivity.this, AnnouncementActivity.class));
                vibrate();
            }
        });

        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Guide Me Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(DashboardActivity.this, GuideMeActivity.class));
                vibrate();
            }
        });

        seizureEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Ask Me Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(DashboardActivity.this, AskMeActivity.class));
                vibrate();
            }
        });
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device has a vibrator
        if (vibrator != null) {
            // Vibrate for 500 milliseconds (adjust duration as needed)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(500);
            }
        }
    }
}