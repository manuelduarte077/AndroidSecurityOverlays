package com.example.androidsecurityoverlays;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OverlayService extends Service {
    private WindowManager windowManager;
    private View overlayView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        // Configura los parámetros para el overlay
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // Añade el overlay a la ventana
        windowManager.addView(overlayView, params);

        // Mostrar mensaje de confirmación
        showOverlayStatusMessage();

        // Configura el botón
        Button testButton = overlayView.findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir la acción que se ejecutará al pulsar el botón
                Toast.makeText(OverlayService.this, "¡Botón presionado!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el TextView para cualquier otra interacción si es necesario
        TextView overlayText = overlayView.findViewById(R.id.overlay_text);
        overlayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OverlayService.this, "Texto tocado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOverlayStatusMessage() {
        // Verifica si el overlay está visible y muestra un mensaje
        if (isOverlayVisible()) {
            Toast.makeText(this, "Overlay está en ejecución", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Overlay no está visible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) windowManager.removeView(overlayView);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean isOverlayVisible() {
        return overlayView != null;
    }
}