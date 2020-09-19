package ru.techcrat.musicproject.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.ui.main.adapters.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_CODE->{
                if (grantResults.isNotEmpty()&&(grantResults[0]==PackageManager.PERMISSION_GRANTED)){this.onResume()}
                else {this.finish()}
            }

        }
    }
}
