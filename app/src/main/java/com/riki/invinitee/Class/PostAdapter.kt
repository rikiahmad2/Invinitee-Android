package com.riki.invinitee.Class

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.riki.invinitee.R
import com.riki.invinitee.Retrofit.DetailBukuTamu
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.riki.invinitee.DetailTamuActivity
import com.squareup.picasso.Picasso


class PostAdapter (private val context: Context, private val list:ArrayList<DetailBukuTamu>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    private lateinit var sharedpref : PreferencesHelper

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(postResponse: DetailBukuTamu){
            sharedpref = PreferencesHelper(context)
            with(itemView){

                //Get Widget
                val iv_image : ImageView = findViewById(R.id.iv_image)
                val tv_nama_tamu : TextView = findViewById(R.id.tv_namatamu)
                val tv_suhu : TextView = findViewById(R.id.tv_suhu)
                val tv_tamuhadir : TextView = findViewById(R.id.tv_tamuhadir)
                var url : String? = postResponse.foto

                tv_nama_tamu.text = postResponse.nama_tamu
                tv_suhu.text = "Suhu : ${postResponse.suhu}°C"
                tv_tamuhadir.text = "${postResponse.jumlah_tamu_hadir} Orang"
                if(!url.isNullOrEmpty()) {
                    Picasso.get().load(url).into(iv_image)
                }

                itemView.setOnClickListener {
                    val bundle = Bundle()
                    val intent = Intent(context, DetailTamuActivity::class.java)
                    bundle.putString("id_buku_tamu", postResponse.id_buku_tamu.toString())
                    bundle.putString("id_tamu", postResponse.id_tamu.toString())
                    bundle.putString("suhu", postResponse.suhu)
                    bundle.putString("jumlah_tamu_hadir", postResponse.jumlah_tamu_hadir)
                    bundle.putString("nama_tamu", postResponse.nama_tamu)
                    bundle.putString("no_wa_tamu", postResponse.no_wa_tamu)
                    bundle.putString("status_tamu", postResponse.status_tamu)
                    bundle.putString("nama_sesi", postResponse.nama_sesi)
                    bundle.putString("foto", postResponse.foto)
                    bundle.putString("nama_kedua_mempelai", postResponse.nama_kedua_mempelai)
                    bundle.putString("created_at", postResponse.created_at)
                    intent.putExtras(bundle)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_list, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }
}