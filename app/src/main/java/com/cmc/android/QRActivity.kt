package com.cmc.android

import android.content.Intent
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

        initQRSetting(savedInstanceState)
        initClickListener()

        setContentView(binding.root)
    }

    private fun initQRSetting(savedInstanceState: Bundle?) {
        capture = CaptureManager(this, binding.zxingBarcodeScanner)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.setShowMissingCameraPermissionDialog(true)
        capture!!.decode()

        binding.zxingBarcodeScanner.viewFinder.setLaserVisibility(false)
    }

    private fun initClickListener() {
        binding.qrBottomTv.setOnClickListener {
            var bottomSheetNum = BottomSheetNum()
            bottomSheetNum.show(supportFragmentManager, "BottomSheetNum")
            bottomSheetNum.setOnDialogFinishListener(object : BottomSheetNum.OnDialogFinishListener {
                override fun finish(result: Boolean) {
                    if (result) {
                        var intent = Intent(this@QRActivity, ResultActivity::class.java)
                        intent.putExtra("title", "00님의 \n출석이 완료되었어요!")
                        intent.putExtra("content", "잠시 후 시작되는 세션에 집중해주세요 :)")
                        intent.putExtra("btnText", "완료")
                        startActivity(intent)
                        finish()
                    }
                }
            })
        }
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