package com.riki.invinitee.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.riki.invinitee.*
import com.riki.invinitee.Class.GeneralClass
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragments : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedpref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedpref = PreferencesHelper(requireContext())
        val v :View = inflater.inflate(R.layout.fragment_profile_fragments, container, false)
        val linear_logout : LinearLayout = v.findViewById(R.id.linear_logout)
        val nama_user : TextView = v.findViewById(R.id.nama_user)
        val profile_email : TextView = v.findViewById(R.id.profile_email)
        val tv_nomor_wa : TextView = v.findViewById(R.id.tv_nomor_wa)
        val linear_bantuan : LinearLayout = v.findViewById(R.id.linear_bantuan)

        //FORMATER TANGGAL
        val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
        val date: LocalDate = LocalDate.parse(sharedpref.getDataString(Constants.PREF_CREATED_AT), inputFormatter)
        val formattedDate: String = outputFormatter.format(date)

        //PROFILE
        nama_user.text = sharedpref.getDataString(Constants.PREF_NAME)
        profile_email.text = sharedpref.getDataString(Constants.PREF_EMAIL)
        tv_nomor_wa.text = sharedpref.getDataString(Constants.PREF_NOMOR_WA)


        //Lisetener
        linear_logout.setOnClickListener {
            val general = GeneralClass(requireActivity())
            general.confirmLogout("Apakah Anda Yakin Ingin Logout ?", LoginActivity::class.java, requireContext(), requireActivity())
        }
        linear_bantuan.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("link", "https://wa.me/6282124241745")
            val intent = Intent(requireActivity(),WebViewActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}