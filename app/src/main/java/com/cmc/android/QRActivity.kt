package com.cmc.android

import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityQrBinding
import com.journeyapps.barcodescanner.CaptureManager

class QRActivity : AppCompatActivity() {

    lateinit var binding: ActivityQrBinding
    private var capture: CaptureManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)

        capture = CaptureManager(this, binding.zxingBarcodeScanner)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.setShowMissingCameraPermissionDialog(true)
        capture!!.decode()

        binding.zxingBarcodeScanner.viewFinder.setLaserVisibility(false)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return binding.zxingBarcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}