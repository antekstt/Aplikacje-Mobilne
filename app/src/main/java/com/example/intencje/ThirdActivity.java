package com.example.intencje;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private TextView txtSzerokosc, txtDlugosc;
    private LocationManager GPSmanager;
    private LocationListener GPSlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        txtSzerokosc = findViewById(R.id.txt_szerokosc);
        txtDlugosc = findViewById(R.id.txt_dlugosc);

        GPSmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSlistener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String szerokosc = String.valueOf(location.getLatitude());
                String dlugosc = String.valueOf(location.getLongitude());

                txtSzerokosc.setText("Szerokość: " + szerokosc);
                txtDlugosc.setText("Długość: " + dlugosc);
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ThirdActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }
                GPSmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GPSlistener);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
    }
}