package com.riki.invinitee.Retrofit

data class LoginAndroid(
    val message: String?,
    val token: String?,
    val data : DetailLogin
)

data class DetailLogin(
    val id : Int?,
    val name : String?,
    val role : String?,
    val nomor_wa : String?,
    val foto : String?,
    val kota : String?,
    val email : String?,
    val email_verified_at : String?,
    val password : String?,
    val created_at : String?,
    val updated_at : String?,
    val has_undangan : ArrayList<HasUndangan>,
)

data class HasUndangan(
    val id_undangan: String?
)

data class BukuTamu(
    val message : String?,
    val code : String?,
    val data :ArrayList<DetailBukuTamu>
)

data class DetailBukuTamu(
    val id_buku_tamu : Int?,
    val id_tamu : Int?,
    val id_undangan : Int?,
    val suhu : String?,
    val foto : String?,
    val jumlah_tamu_hadir : String?,
    val created_at: String?,
    val nama_tamu : String?,
    val status_tamu : String?,
    val nama_sesi : String?,
    val nama_kedua_mempelai : String?,
    val no_wa_tamu : String?,
)

data class storedScan(
    val message: String?,
    val token: String?,
    val data : DetailStored
)

data class DetailStored(
    val foto : String?,
    val id_tamu : String?,
    val suhu : String?,
    val jumlah_tamu_hadir : String?,
    val updated_at : String?,
    val created_at : String?,
    val id_buku_tamu : String?,
)