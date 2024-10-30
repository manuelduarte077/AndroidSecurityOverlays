package com.example.tapjackingtest

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var locationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vulnerableButton: Button = findViewById(R.id.vulnerableButton)
        val overlayView: View = findViewById(R.id.overlayView)
        val locationStatus: TextView = findViewById(R.id.locationStatus)

        // Ejemplo 1: Permiso
        vulnerableButton.setOnClickListener {
            Toast.makeText(this, "¡Permiso concedido!", Toast.LENGTH_SHORT).show()
        }

        // Ejemplo 2: Compra in-app
        val buyButton: Button = findViewById(R.id.buyButton)
        buyButton.setOnClickListener {
            Toast.makeText(this, "¡Compra realizada!", Toast.LENGTH_SHORT).show()
        }

        // Ejemplo 3: Configuración (Localización)
        val locationButton: Button = findViewById(R.id.locationButton)
        locationButton.setOnClickListener {
            locationEnabled = !locationEnabled
            updateLocationStatus()
        }

        // Overlay (común para todos los ejemplos)
        overlayView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                println("Overlay recibió el toque, pero lo ignora.")
            }
            false
        }
        updateLocationStatus()

        // Ejemplo 4: Phishing (se configura en el XML con un TextView sobre el botón)
        val phishingButton: Button = findViewById(R.id.phishingButton)
        phishingButton.setOnClickListener {
            Toast.makeText(this, "¡Acción no deseada realizada!", Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateLocationStatus() {
        val locationStatus: TextView = findViewById(R.id.locationStatus)
        val statusText =
            if (locationEnabled) "Localización activada" else "Localización desactivada"
        locationStatus.text = statusText
    }
}