package com.hispacode.filmica.view.films

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.view.watchlist.WatchlistFragment
import com.hispacode.filmica.view.detail.DetailActivity
import com.hispacode.filmica.view.detail.DetailFragment
import kotlinx.android.synthetic.main.activity_films.*

const val TAG_FILM = "films"
const val TAG_WATCHLIST = "watchlist"

class FilmsActivity : AppCompatActivity(),
    FilmsFragment.OnFilmClickListener {

    private lateinit var filmsFragment: FilmsFragment
    private lateinit var watchlistFragment: WatchlistFragment
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if (savedInstanceState == null) {
            setupFragments()
        } else {
            val tag =savedInstanceState.getString("active", TAG_FILM)
            restoreFragments(tag)
        }
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_discover -> showMainFragment(filmsFragment)
                R.id.action_watchlist -> showMainFragment(watchlistFragment)
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("active",activeFragment.tag)
    }

    private fun restoreFragments(tag: String) {
        filmsFragment = supportFragmentManager.findFragmentByTag(TAG_FILM) as FilmsFragment
        watchlistFragment = supportFragmentManager.findFragmentByTag(TAG_WATCHLIST) as WatchlistFragment
        activeFragment =
            if (tag == TAG_WATCHLIST)  watchlistFragment
            else filmsFragment
    }

    private fun setupFragments() {
        filmsFragment = FilmsFragment()
        watchlistFragment = WatchlistFragment()
        activeFragment = filmsFragment

        supportFragmentManager.beginTransaction()
            .add(R.id.container, filmsFragment, TAG_FILM)
            .add(R.id.container, watchlistFragment, TAG_WATCHLIST)
            .hide(watchlistFragment)
            .commit()
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