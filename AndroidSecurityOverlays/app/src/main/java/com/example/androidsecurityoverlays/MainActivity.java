package com.example.androidsecurityoverlays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startOverlayButton = findViewById(R.id.start_overlay_button);
        startOverlayButton.setOnClickListener(v -> {
            if (Settings.canDrawOverlays(this)) {
                startOverlayService();
            } else {
                requestOverlayPermission();
            }
        });
    }

    private void requestOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
    }

    private void startOverlayService() {
        Intent overlayIntent = new Intent(this, OverlayService.class);
        startService(overlayIntent);
        Toast.makeText(this, "Overlay service started!", Toast.LENGTH_SHORT).show();
        System.out.println("Overlay service started!");
        Log.e("MainActivity", "Overlay service started!");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                startOverlayService();
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                System.out.println("Permission granted!");
                Log.e("MainActivity", "Permission granted!");
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                System.out.println("Permission not granted!");
                Log.e("MainActivity", "Permission not granted!");
            }
        }
    }
}