package com.hispacode.filmica.view.films

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.view.WatchList.WatchlistFragment
import com.hispacode.filmica.view.detail.DetailActivity
import com.hispacode.filmica.view.detail.DetailFragment
import kotlinx.android.synthetic.main.activity_films.*

class FilmsActivity : AppCompatActivity(),
    FilmsFragment.OnFilmClickListener {
    private lateinit var filmsFragment: FilmsFragment
    private lateinit var watchlistFragment: WatchlistFragment
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if (savedInstanceState == null) {
            filmsFragment = FilmsFragment()
            watchlistFragment = WatchlistFragment()
            activeFragment = filmsFragment

            supportFragmentManager.beginTransaction()
                .add(R.id.container, filmsFragment)
                .add(R.id.container, watchlistFragment)
                .hide(watchlistFragment)
                .commit()
        }
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_discover -> showMainFragment(filmsFragment)
                R.id.action_watchlist -> showMainFragment(watchlistFragment)
            }
            true
        }
    }

    private fun showMainFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()

        activeFragment = fragment
    }
    //Is Tablet??
    override fun onClick(film: Film) {
        if (!isDetailViewAvailable()) {

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", film.id)
            startActivity(intent)

        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_detail,
                    DetailFragment.newInstance(film.id)
                )
                .commit()
        }
    }

    private fun isDetailViewAvailable() =
        findViewById<FrameLayout>(R.id.container_detail) != null

}