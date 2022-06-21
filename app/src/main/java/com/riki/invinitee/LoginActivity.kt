package com.riki.invinitee

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.riki.invinitee.Class.GeneralClass
import com.riki.invinitee.Retrofit.LoginAndroid
import com.riki.invinitee.Retrofit.RetrofitClient
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import cn.pedant.SweetAlert.SweetAlertDialog




class LoginActivity : AppCompatActivity() {
    private lateinit var sharedpref : PreferencesHelper
    private var tvUsername : TextView? = null
    private var tvPassword : TextInputLayout? = null
    private var stringPass : String? = null
    private var sButton : Button? = null
    private var tv_daftar : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedpref = PreferencesHelper(this)

        //Function
        getLoginWidget()
        listenerCollection()

    }

    //Get Widget From Login Activity
    private fun getLoginWidget()
    {
        tvUsername = findViewById(R.id.tvUsername)
        tvPassword = findViewById(R.id.tvPassword)
        sButton = findViewById(R.id.sButton)
        tv_daftar = findViewById(R.id.tv_daftar)
    }

    //listener for Login Activity
    private fun listenerCollection()
    {
        tvPassword?.getEditText()?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                //get the String from CharSequence with s.toString() and process it to validation
                stringPass = s.toString()
            }
        })

        //TOMBOL DAFTAR
        tv_daftar?.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("link", "https://invinitee.id")
            val intent = Intent(this,WebViewActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //TOMBOL LOGIN DITEKAN
        sButton?.setOnClickListener {
            if(tvUsername?.text.toString() != "" && stringPass != ""){
                RetrofitClient.instance.loginAndroid(
                    tvUsername?.text.toString(),
                    stringPass
                ).enqueue(object  : Callback<LoginAndroid> {
                    override fun onFailure(call: Call<LoginAndroid>, t: Throwable) {
                        val general = GeneralClass(this@LoginActivity)
                        t.message?.let { general.showToast(applicationContext,it) }
                    }
                    override fun onResponse(
                        call: Call<LoginAndroid>,
                        response: Response<LoginAndroid>
                    )
                    {
                        val responseCode  = response.code().toString()
                        if(responseCode == "200") {
                            //Save Session Ketika Login Berhasil
                            saveSession(Constants.PREF_ID, response.body()?.data?.id.toString())
                            saveSession(Constants.PREF_TOKEN, response.body()?.token.toString())
                            saveSession(Constants.PREF_NAME, response.body()?.data?.name.toString())
                            saveSession(Constants.PREF_ROLE, response.body()?.data?.role.toString())
                            saveSession(Constants.PREF_FOTO, response.body()?.data?.foto.toString())
                            saveSession(
                                Constants.PREF_NOMOR_WA,
                                response.body()?.data?.nomor_wa.toString()
                            )
                            saveSession(Constants.PREF_KOTA, response.body()?.data?.kota.toString())
                            saveSession(
                                Constants.PREF_EMAIL,
                                response.body()?.data?.email.toString()
                            )
                            saveSession(
                                Constants.PREF_CREATED_AT,
                                response.body()?.data?.created_at.toString()
                            )
                            saveSession(
                                Constants.PREF_UPDATED_AT,
                                response.body()?.data?.updated_at.toString()
                            )
                            saveSession(
                                Constants.PREF_ID_UNDANGAN,
                                response.body()?.data?.has_undangan?.get(0)?.id_undangan.toString()
                            )
                            sharedpref.simpanDataBoolean(Constants.PREF_IS_LOGIN, true)
                            val pDialog = SweetAlertDialog(this@LoginActivity, SweetAlertDialog.PROGRESS_TYPE)
                            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                            pDialog.titleText = "Loading"
                            pDialog.setCancelable(false)
                            pDialog.show()

                            Handler().postDelayed({
                                pDialog.dismiss()
                                moveIntent(this@LoginActivity, DashboardActivity::class.java)
                                finish()
                            }, 2000)
                        }
                        else
                        {
                            val pDialog = SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                            pDialog.progressHelper.barColor = Color.parseColor("#FF0000")
                            pDialog.titleText = "Kombinasi Tidak Ditemukan"
                            pDialog.setCancelable(true)
                            pDialog.show()
                        }
                    }
                })
            }
            else
            {
                val pDialog = SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                pDialog.progressHelper.barColor = Color.parseColor("#FF0000")
                pDialog.titleText = "Server Tidak Merespon, Pastikan Internetmu Menyala"
                pDialog.setCancelable(true)
                pDialog.show()
            }
        }
    }

    fun saveSession(key : String, value : String){
        sharedpref.simpanDataString(key, value)
    }

    private fun moveIntent(intent: Activity, classs: Class<*>?){
        startActivity(Intent(intent,classs))
    }

    override fun onStart() {
        super.onStart()
        if (sharedpref.getDataBoolean(Constants.PREF_IS_LOGIN) == true){
            finish()
            moveIntent(this,DashboardActivity::class.java)
        }
    }
}