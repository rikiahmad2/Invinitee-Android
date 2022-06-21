package com.riki.invinitee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riki.invinitee.Class.PostAdapter
import com.riki.invinitee.Retrofit.*
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BukuTamuActivity : AppCompatActivity() {
    private val list = ArrayList<DetailOldBukuTamu>()
    private var rv_recyclerView : RecyclerView? = null
    private lateinit var sharedpref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku_tamu)

        getWidget()
        requestAllBukuTamu()
    }

    //GET ITEM WIDGET
    private fun getWidget()
    {
        rv_recyclerView = findViewById(R.id.rv_recyclerView)
        rv_recyclerView?.layoutManager = LinearLayoutManager(this)
        sharedpref = PreferencesHelper(this)
    }

    //API REQUEST
    private fun requestAllBukuTamu()
    {
        //API RETROFIT
        RetrofitClient.instance.getOldBukuTamu(
            sharedpref.getDataString(Constants.PREF_ID_UNDANGAN).toString(),
            "DESC",
            token = "Bearer ${sharedpref.getDataString(Constants.PREF_TOKEN)}"
        ).enqueue(object : Callback<BukuTamuOld> {
            override fun onFailure(call: Call<BukuTamuOld>, t: Throwable) {
                Log.d("Response Gagal", "Request Gagal")
            }
            override fun onResponse(
                call: Call<BukuTamuOld>,
                response: Response<BukuTamuOld>
            ) {
                val responseCode  = response.code().toString()
                if(responseCode == "200") {
                    response.body()?.let { list.addAll((it.data)) }
                    val adapter = PostAdapter(this@BukuTamuActivity,list)
                    rv_recyclerView?.adapter = adapter
                    Log.d("TAG/DATA", list.toString())
                }
                else
                {
                    Log.d("Bad Response", response.body()?.message.toString()+responseCode)
                    Log.d("TOKEN", sharedpref.getDataString(Constants.PREF_TOKEN).toString())
                }
            }
        })
    }
}