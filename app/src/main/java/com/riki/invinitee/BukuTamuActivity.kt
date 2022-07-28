package com.riki.invinitee

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.barteksc.pdfviewer.PDFView
import com.riki.invinitee.Class.PostAdapter
import com.riki.invinitee.Retrofit.*
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BukuTamuActivity : AppCompatActivity() {
    private val list = ArrayList<DetailBukuTamu>()
    private var rv_recyclerView : RecyclerView? = null
    private lateinit var sharedpref : PreferencesHelper
    private var calendar : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku_tamu)

        getWidget()
        requestAllBukuTamu()
        listenerCollection()
    }

    //GET ITEM WIDGET
    private fun getWidget()
    {
        rv_recyclerView = findViewById(R.id.rv_recyclerView)
        rv_recyclerView?.layoutManager = LinearLayoutManager(this)
        sharedpref = PreferencesHelper(this)
        calendar = findViewById(R.id.calendar)
    }

    //API REQUEST
    private fun requestAllBukuTamu()
    {
        //API RETROFIT
        RetrofitClient.instance.getAllBukuTamu(
            sharedpref.getDataString(Constants.PREF_ID_UNDANGAN).toString(),
            "DESC",
            token = "Bearer ${sharedpref.getDataString(Constants.PREF_TOKEN)}"
        ).enqueue(object : Callback<BukuTamu> {
            override fun onFailure(call: Call<BukuTamu>, t: Throwable) {
                Log.d("Response Gagal", "Request Gagal")
            }
            override fun onResponse(
                call: Call<BukuTamu>,
                response: Response<BukuTamu>
            ) {
                val responseCode  = response.code().toString()
                if(responseCode == "200") {
                    response.body()?.let { list.addAll((it.data)) }
                    val adapter = PostAdapter(this@BukuTamuActivity,list)
                    rv_recyclerView?.adapter = adapter
                }
                else
                {
                    val pDialog = SweetAlertDialog(this@BukuTamuActivity, SweetAlertDialog.ERROR_TYPE)
                    pDialog.progressHelper.barColor = Color.parseColor("#FF0000")
                    pDialog.titleText = "Gagal, Periksa Koneksi Anda"
                    pDialog.setCancelable(false)
                    pDialog.show()
                }
            }
        })
    }

    private fun requestPDF()
    {
        //API RETROFIT
        RetrofitClient.instance.downloadPDF(
            token = "Bearer ${sharedpref.getDataString(Constants.PREF_TOKEN)}",
            sharedpref.getDataString(Constants.PREF_ID_UNDANGAN),
        ).enqueue(object : Callback<downloadPDF> {
            override fun onFailure(call: Call<downloadPDF>, t: Throwable) {
                Log.d("Response Gagal", "Request Gagal")
            }
            override fun onResponse(
                call: Call<downloadPDF>,
                response: Response<downloadPDF>
            ) {
                val responseCode  = response.code().toString()
                if(responseCode == "200") {
                    Log.d("Response Sukses", response.body()?.pdf.toString())
                }
                else
                {
                    Log.d("Response Gagal", response.body()?.pdf.toString())
                }
            }
        })
    }

    private fun listenerCollection()
    {
        calendar?.setOnClickListener {
            Log.d("Log Click", "Log Berhasil")
            moveIntent(this, PDFViewerActivity::class.java)
        }
    }

    private fun moveIntent(intent: Activity, classs: Class<*>?){
        startActivity(Intent(intent,classs))
    }
}