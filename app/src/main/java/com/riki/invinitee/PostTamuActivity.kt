package com.riki.invinitee

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import androidx.activity.result.contract.ActivityResultContracts
import com.riki.invinitee.Class.GeneralClass
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.riki.invinitee.Helper.MyFunctions
import com.riki.invinitee.Retrofit.RetrofitClient
import com.riki.invinitee.Retrofit.oldScan
import com.riki.invinitee.Retrofit.storedScan
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class PostTamuActivity : AppCompatActivity() {
    private var addphoto : ImageView? = null
    private var imageView2: ImageView? = null
    private var id_tamu : String? = null
    private var sButton : Button? = null
    private var bitmap : Bitmap? = null
    private var edit_suhu : EditText? = null
    private var edit_jumlah : EditText? = null
    private val cameraRequestId = 1222
    private lateinit var sharedpref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_tamu)
        getWidget()

        id_tamu = intent.getStringExtra("id_tamu")
        addphoto?.setOnClickListener{
            openSomeActivityForResult()
        }
        sButton?.setOnClickListener {
            storedScan()
        }
    }

    fun openSomeActivityForResult() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bundle : Bundle? = result.data?.extras
            bitmap = bundle?.get("data") as Bitmap
            imageView2?.setImageBitmap(bitmap)
        }
        else
        {
            val general = GeneralClass(this)
            general.showToast(applicationContext, "Foto gagal")
        }
    }

    private fun getWidget()
    {
        addphoto = findViewById(R.id.addphoto)
        imageView2 = findViewById(R.id.imageView2)
        sharedpref = PreferencesHelper(this)
        sButton = findViewById(R.id.sButton)
        edit_suhu = findViewById(R.id.edit_suhu)
        edit_jumlah = findViewById(R.id.edit_jumlah)
    }

    private fun storedScan()
    {
        val image = MyFunctions.bitmapToBase64(bitmap)
        //API RETROFIT
        RetrofitClient.instance.storedOldScan(
            token = "Bearer ${sharedpref.getDataString(Constants.PREF_TOKEN)}",
            id_tamu,
            image,
            edit_suhu?.text.toString(),
            edit_jumlah?.text.toString(),
        ).enqueue(object : Callback<oldScan> {
            override fun onFailure(call: Call<oldScan>, t: Throwable) {
                Log.d("Response Gagal", "Request Gagal")
            }
            override fun onResponse(
                call: Call<oldScan>,
                response: Response<oldScan>
            ) {
                val responseCode  = response.code().toString()
                if(responseCode == "200") {
                    val pDialog = SweetAlertDialog(this@PostTamuActivity, SweetAlertDialog.PROGRESS_TYPE)
                    val sDialog = SweetAlertDialog(this@PostTamuActivity, SweetAlertDialog.SUCCESS_TYPE)
                    pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                    pDialog.titleText = "Loading"
                    pDialog.setCancelable(false)
                    pDialog.show()
                    Handler().postDelayed({
                        pDialog.dismiss()
                        sDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                        sDialog.titleText = "Success"
                        sDialog.setCancelable(false)
                        sDialog.show()
                        Handler().postDelayed({
                            moveIntent(this@PostTamuActivity, ScanActivity::class.java)
                        }, 2000)
                    }, 2000)
                }
                else
                {
                    val pDialog = SweetAlertDialog(this@PostTamuActivity, SweetAlertDialog.ERROR_TYPE)
                    pDialog.progressHelper.barColor = Color.parseColor("#FF0000")
                    pDialog.titleText = "Gagal, Periksa Koneksi Anda"
                    pDialog.setCancelable(true)
                    pDialog.show()
                }
            }
        })
    }

    private fun moveIntent(intent: Activity, classs: Class<*>?){
        startActivity(Intent(intent,classs))
        finish()
    }

}