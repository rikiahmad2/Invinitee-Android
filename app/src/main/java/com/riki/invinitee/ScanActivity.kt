package com.riki.invinitee

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.riki.invinitee.Class.GeneralClass
import com.journeyapps.barcodescanner.ScanOptions

import com.journeyapps.barcodescanner.ScanContract

import androidx.activity.result.ActivityResultLauncher
import com.journeyapps.barcodescanner.ScanIntentResult


class ScanActivity : AppCompatActivity() {
    private var scanUser : Button? = null
    private var backBtn : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        //Widget
        getWidget()

        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        options.setPrompt("Scan Barcode Tamu")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)

        //listener
        listenerCollection()
    }

    // (SCAN BARCODE)
    private val barcodeLauncher: ActivityResultLauncher<ScanOptions?>? = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@ScanActivity, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@ScanActivity, "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()

            //Delay Handler after 2 seconds
            val handler = Handler()
            handler.postDelayed(object : Runnable{
                override fun run() {
                    val intent = Intent(this@ScanActivity,PostTamuActivity::class.java)
                    intent.putExtra("id_tamu",result.contents)
                    startActivity(intent)
                }
            }, 2000)
        }
    }


    private fun listenerCollection()
    {
        scanUser?.setOnClickListener{
            barcodeLauncher!!.launch(ScanOptions())
        }
        backBtn?.setOnClickListener {
            moveIntent(this@ScanActivity, DashboardActivity::class.java)
            finish()
        }
    }

    private fun getWidget()
    {
        scanUser = findViewById(R.id.scanUser)
        backBtn = findViewById(R.id.backBtn)
    }

    private fun moveIntent(intent: Activity, classs: Class<*>?){
        startActivity(Intent(intent,classs))
    }
}