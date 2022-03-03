package com.riki.invinitee.Retrofit

import android.graphics.Bitmap
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("auth/login")
    fun loginAndroid(
        @Field("email") username : String?,
        @Field("password") password : String?
    ) : Call<LoginAndroid>

    @GET("v1/bukutamu/getbukutamus")
    fun getAllBukuTamu(
        @Query("filter_id_undangan") id_undangan : String,
        @Query("order_by") order_by : String,
        @Header("Authorization") token: String?
    ): Call<BukuTamu>

    @FormUrlEncoded
    @POST("v1/bukutamu/createbukutamu")
    fun storedScan(
        @Header("Authorization") token: String?,
        @Field("id_tamu") id_tamu: String?,
        @Field("foto_base") foto_base: String?,
        @Field("suhu") suhu: String?,
        @Field("jumlah_tamu_hadir") jumlah_tamu_hadir: String?,
    ) : Call<storedScan>
}