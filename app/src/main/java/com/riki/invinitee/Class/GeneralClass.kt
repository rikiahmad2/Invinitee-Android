package com.riki.invinitee.Class

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import com.riki.invinitee.DashboardActivity
import com.riki.invinitee.R
import com.riki.invinitee.SharedPreferences.PreferencesHelper


class GeneralClass(val mActivity : Activity) {
    private  lateinit var isDialog: AlertDialog
    private lateinit var sharedpref : PreferencesHelper

    fun showToast(thiscontext : Context, message : String){
        Toast.makeText(thiscontext,message, Toast.LENGTH_SHORT).show()
    }

    fun confirmLogout(message: String, classs: Class<*>?, conn : Context, inna : Activity){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.confirm_logout, null)
        val builder = AlertDialog.Builder(mActivity)
        val cancelButton : Button = dialogView.findViewById(R.id.cancelBtn)
        val logoutButton : Button = dialogView.findViewById(R.id.logoutBtn)
        val tv : TextView = dialogView.findViewById(R.id.confirm_logout_tv)
        sharedpref = PreferencesHelper(conn)

        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
        tv.text = message

        cancelButton.setOnClickListener {
            dismisLoading()
        }
        logoutButton.setOnClickListener {
            sharedpref.clearSession()
            inna.finish()
            conn.startActivity(Intent(inna,classs))

        }
    }

    fun dismisLoading(){
        isDialog.dismiss()
    }
}