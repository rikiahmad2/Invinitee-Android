package com.riki.invinitee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riki.invinitee.Fragments.DashboardContent
import com.riki.invinitee.Fragments.ProfileFragments

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //GET FRAGMENT
        val dashboardFragment = DashboardContent()
        val profileFragment = ProfileFragments()
        makeCurrentFragments(dashboardFragment)
        val bottom_nav : BottomNavigationView = findViewById(R.id.bottom_nav)

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> makeCurrentFragments(dashboardFragment)
                R.id.menu_profile -> makeCurrentFragments(profileFragment)
            }
            true
        }

    }

    private fun makeCurrentFragments(fragment : Fragment)
    {
        val bundle = Bundle()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment, fragment).addToBackStack("Tag")
            commit()
            fragment.setArguments(bundle)
        }
    }
}