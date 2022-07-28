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

    @GET("v1/applama/getguest")
    fun getOldBukuTamu(
        @Query("filter_id_undangan") id_undangan : String,
        @Query("order_by") order_by : String,
        @Header("Authorization") token: String?
    ): Call<BukuTamuOld>

    @FormUrlEncoded
    @POST("v1/applama/scantamu")
    fun storedOldScan(
        @Header("Authorization") token: String?,
        @Field("id_tamu") id_tamu: String?,
        @Field("foto_base") foto_base: String?,
        @Field("suhu") suhu: String?,
        @Field("tamu_hadir") jumlah_tamu_hadir: String?,
    ) : Call<oldScan>

    @FormUrlEncoded
    @POST("v1/bukutamu/createbukutamu")
    fun storedScan(
        @Header("Authorization") token: String?,
        @Field("id_tamu") id_tamu: String?,
        @Field("foto_base") foto_base: String?,
        @Field("suhu") suhu: String?,
        @Field("jumlah_tamu_hadir") jumlah_tamu_hadir: String?,
    ) : Call<storedScan>

    @DELETE("v1/bukutamu/deletebukutamu/{id_buku_tamu}")
    fun deleteBukuTamu(
        @Header("Authorization") token: String?,
        @Path("id_buku_tamu") id_buku_tamu: String?,
    ) : Call<DeleteBuku>

    @GET("v1/bukutamu/getbukutamupdf/{id_undangan}")
    fun downloadPDF(
        @Header("Authorization") token: String?,
        @Path("id_undangan") id_undangan: String?,
    ) : Call<downloadPDF>
}