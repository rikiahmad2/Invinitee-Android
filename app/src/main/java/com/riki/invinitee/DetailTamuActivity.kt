package com.riki.invinitee

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.net.URL


class DetailTamuActivity : AppCompatActivity() {
    private var tvJudul : TextView? = null
    private var tvNamaTamu : TextView? = null
    private var tvJumlahHadir : TextView? = null
    private var tvSuhu : TextView? = null
    private var tvStatus : TextView? = null
    private var tvNamaSesi : TextView? = null
    private var tvWaktuScan : TextView? = null
    private var imgLogin : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tamu)

        getWidget()
        val b = intent.extras
        val nama_tamu = b!!.getString("nama_tamu")
        val jumlah_tamu_hadir = b!!.getString("jumlah_tamu_hadir")
        val suhu = b!!.getString("suhu")
        val status_tamu = b!!.getString("status_tamu")
        val created_at = b!!.getString("created_at")
        val foto = b!!.getString("foto")

        tvJudul?.text = "Wedding Of Predy & Tri Dewi"
        tvNamaTamu?.text = nama_tamu
        tvJumlahHadir?.text = "${jumlah_tamu_hadir} Orang"
        tvSuhu?.text = "Suhu : ${suhu}°C"
        tvStatus?.text = "Status Tamu : ${status_tamu}"
        tvWaktuScan?.text = "Di Scan Pada : ${created_at}"
        tvNamaSesi?.text = "Nama Sesi : -"
        Picasso.get().load("https://www.seekpng.com/png/detail/73-730482_existing-user-default-avatar.png").into(imgLogin)
        if(!foto.isNullOrEmpty()) {
            Picasso.get().load(foto).into(imgLogin);
        }
        if(status_tamu.isNullOrEmpty()){
            tvStatus?.text = "Status Tamu : Normal"
        }

    }

    private fun getWidget()
    {
        tvJudul = findViewById(R.id.tvJudul)
        tvNamaTamu = findViewById(R.id.tvNamaTamu)
        tvJumlahHadir = findViewById(R.id.tvJumlahHadir)
        tvSuhu = findViewById(R.id.tvSuhu)
        tvStatus = findViewById(R.id.tvStatus)
        tvNamaSesi = findViewById(R.id.tvNamaSesi)
        tvWaktuScan = findViewById(R.id.tvWaktuScan)
        imgLogin = findViewById(R.id.imgLogin)
    }
}