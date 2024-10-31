package com.example.androidsecurityoverlays;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

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
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // Posiciona el overlay en la parte superior
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 50;
        params.y = 50;

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
                Toast.makeText(OverlayService.this, "¡Botón presionado!", Toast.LENGTH_LONG).show();
                System.out.println("¡Botón presionado!");
                Log.d("OverlayService", "¡Botón presionado!");
            }
        });

        // Configura el TextView para cualquier otra interacción si es necesario
        TextView overlayText = overlayView.findViewById(R.id.overlay_text);
        overlayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OverlayService.this, "Texto tocado!", Toast.LENGTH_LONG).show();
                System.out.println("Texto tocado!");
                Log.d("OverlayService", "Texto tocado!");
            }
        });
    }

    private void showOverlayStatusMessage() {
        // Verifica si el overlay está visible y muestra un mensaje
        if (isOverlayVisible()) {
            Toast.makeText(this, "Overlay está en ejecución", Toast.LENGTH_LONG).show();
            System.out.println("Overlay está en ejecución");
            Log.d("OverlayService", "Overlay está en ejecución");
        } else {
            Toast.makeText(this, "Overlay no está visible", Toast.LENGTH_LONG).show();
            System.out.println("Overlay no está visible");
            Log.d("OverlayService", "Overlay no está visible");
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