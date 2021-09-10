package com.example.rnm.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rnm.R
import com.example.rnm.feature.episode.detail.EpisodeDetailFragment
import com.example.rnm.feature.episode.list.EpisodeListFragment
import com.example.rnm.feature.interf.GoBackFragment
import com.example.rnm.feature.location.detail.LocationDetailFragment
import com.example.rnm.feature.location.list.LocationListFragment
import com.example.rnm.feature.personage.detail.PersonageDetailFragment
import com.example.rnm.feature.personage.list.PersonageListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    PersonageListFragment.ItemPersonageSelected,
    LocationListFragment.ItemLocationSelected,
    EpisodeListFragment.ItemEpisodeSelected,
    PersonageDetailFragment.GoLocation,
    GoBackFragment {
    private lateinit var bottomMenu: BottomNavigationView
    private var personageListFragment = PersonageListFragment()
    private var locationListFragment = LocationListFragment()
    private var episodeListFragment = EpisodeListFragment()

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomMenu = findViewById(R.id.bottomMenu)

        if (savedInstanceState == null) {
            initPersonageFragment()
        }

        bottomMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomMenuPersonage -> {
                    initPersonageFragment()
                    true
                }
                R.id.bottomMenuLocation -> {
                    initLocationFragment()
                    true
                }
                R.id.bottomMenuEpisode -> {
                    initEpisodeFragment()
                    true
                }
                else -> {
                    Toast.makeText(this, " hello", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }

    }

    private fun initPersonageFragment() {
        supportFragmentManager.beginTransaction().run {
            personageListFragment = PersonageListFragment.newInstance()
            replace(R.id.container, personageListFragment)
            commit()
        }
    }

    private fun initLocationFragment() {
        supportFragmentManager.beginTransaction().run {
            locationListFragment = LocationListFragment.newInstance()
            replace(R.id.container, locationListFragment)
            commit()
        }
    }

    private fun initEpisodeFragment() {
        supportFragmentManager.beginTransaction().run {
            episodeListFragment = EpisodeListFragment.newInstance()
            replace(R.id.container, episodeListFragment)
            commit()
        }
    }

    override fun onItemPersonageSelected(id: Int) {
        initPersonageDetails(id)
    }

    override fun onItemEpisodeSelected(id: Int) {
        initEpisodeDetails(id)
    }

    override fun onItemLocationSelected(id: Int) {
        initLocationDetails(id)
    }

    override fun onGoingBack() {
        onBackPressed()
    }

    override fun onGoLocation(myId: Int) {
        initLocationDetails(myId)
    }

    private fun initPersonageDetails(myId: Int) {
        supportFragmentManager.beginTransaction().run {
            val personageDetailsFragment = PersonageDetailFragment.newInstance(myId)
            replace(R.id.container, personageDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun initEpisodeDetails(myId: Int) {
        supportFragmentManager.beginTransaction().run {
            val episodeDetailsFragment = EpisodeDetailFragment.newInstance(myId)
            replace(R.id.container, episodeDetailsFragment)
            addToBackStack(null)
            commit()
        }

    }

    private fun initLocationDetails(myId: Int) {
        supportFragmentManager.beginTransaction().run {
            val locationDetailsFragment = LocationDetailFragment.newInstance(myId)
            replace(R.id.container, locationDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }
}