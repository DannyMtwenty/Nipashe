package com.example.nipashe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        bottomNavigationView=findViewById(R.id.bottomNavigationView)

        val newsNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment

        //connect and setup bottom nav
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}