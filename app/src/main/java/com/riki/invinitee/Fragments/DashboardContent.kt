package com.riki.invinitee.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.riki.invinitee.BukuTamuActivity
import com.riki.invinitee.R
import com.riki.invinitee.ScanActivity
import com.riki.invinitee.SharedPreferences.Constants
import com.riki.invinitee.SharedPreferences.PreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardContent.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardContent : Fragment() {
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
        val v : View = inflater.inflate(R.layout.fragment_dashboard_content, container, false)

        //GET WIDGET
        sharedpref = PreferencesHelper(requireContext())
        val tv_email : TextView = v.findViewById(R.id.tv_email)
        val tv_username : TextView = v.findViewById(R.id.tv_username)
        val scan_tamu : LinearLayout = v.findViewById(R.id.scan_tamu)
        val daftar_tamu : LinearLayout = v.findViewById(R.id.daftar_tamu)
        val btn_settings : LinearLayout = v.findViewById(R.id.btn_settings)
        val profilefrag = ProfileFragments()

        //SLIDER
        var imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://asset.kompas.com/crops/UuADTgF8vBA2xEDwA20yAjF5VV4=/0x0:1000x667/750x500/data/photo/2017/04/21/3305124549.jpg", "Follow Instagram @invinitee_", ScaleTypes.FIT))
        imageList.add(SlideModel("https://image.akurat.co/images/uploads/images/akurat_20200407102044_8686AU.jpg", "Subscribe Channel Invinitee", ScaleTypes.FIT))
        val imageSlider = v.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        imageSlider.startSliding(5000)

        tv_email.text = sharedpref.getDataString(Constants.PREF_EMAIL)
        tv_username.text = sharedpref.getDataString(Constants.PREF_NAME)

        //Listener
        scan_tamu.setOnClickListener{
            moveIntent(requireActivity(), ScanActivity::class.java)
        }
        daftar_tamu.setOnClickListener {
            moveIntent(requireActivity(), BukuTamuActivity::class.java)
        }
        btn_settings.setOnClickListener {
            makeCurrentFragment(profilefrag)
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
         * @return A new instance of fragment DashboardContent.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardContent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun moveIntent(intent: Activity, classs: Class<*>?){
        startActivity(Intent(intent,classs))
    }

    private fun makeCurrentFragment(fragment: Fragment){
        val bundle = Bundle()
        requireFragmentManager().beginTransaction()?.apply {
            replace(R.id.container_fragment, fragment).addToBackStack("Tag")
            commit()
            fragment.setArguments(bundle)
        }
    }
}