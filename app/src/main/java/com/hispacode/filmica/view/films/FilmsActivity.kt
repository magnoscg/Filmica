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
import com.hispacode.filmica.view.detail.PlaceHolderFragment
import com.hispacode.filmica.view.search.SearchFragment
import com.hispacode.filmica.view.trending.TrendingFilmsFragment
import kotlinx.android.synthetic.main.activity_films.*

const val TAG_FILM = "films"
const val TAG_WATCHLIST = "watchlist"
const val TAG_TRENDING = "trending"
const val TAG_SEARCH = "search"

class FilmsActivity : AppCompatActivity(),
    FilmsFragment.OnFilmClickListener {

    private lateinit var filmsFragment: FilmsFragment
    private lateinit var watchlistFragment: WatchlistFragment
    private lateinit var trendingFilmsFragment: TrendingFilmsFragment
    private lateinit var searchFilmsFragment: SearchFragment
    private lateinit var activeFragment: Fragment

    private var detailViewIsSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if (savedInstanceState == null) {
            //setupFragments()
            if (isDetailViewAvailable()) {
                showPlaceholder()
            }
            setupFragments()
        } else {
            if (isDetailViewAvailable() and detailViewIsSelected){
                showPlaceholder()
            }
            val tag =savedInstanceState.getString("active", TAG_FILM)
            restoreFragments(tag)
        }

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_discover -> showMainFragment(filmsFragment, false)
                R.id.action_watchlist -> showMainFragment(watchlistFragment, true)
                R.id.action_trending -> showMainFragment(trendingFilmsFragment, false)
                R.id.action_search -> showMainFragment(searchFilmsFragment, false)
            }
            /*if (isDetailViewAvailable()) {
                showPlaceholder()
            }*/
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
        trendingFilmsFragment = supportFragmentManager.findFragmentByTag(TAG_TRENDING) as TrendingFilmsFragment
        searchFilmsFragment = supportFragmentManager.findFragmentByTag(TAG_SEARCH)  as SearchFragment
        activeFragment =
            when (tag) {
                TAG_WATCHLIST -> watchlistFragment
                TAG_TRENDING -> trendingFilmsFragment
                TAG_SEARCH -> searchFilmsFragment
                else -> filmsFragment
            }
    }
    private fun showPlaceholder() {
        val placeholderFragment = PlaceHolderFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_detail, placeholderFragment)
            .commit()

        detailViewIsSelected = false
    }

    private fun setupFragments() {
        filmsFragment = FilmsFragment()
        watchlistFragment = WatchlistFragment()
        trendingFilmsFragment = TrendingFilmsFragment()
        searchFilmsFragment = SearchFragment()
        activeFragment = filmsFragment

        supportFragmentManager.beginTransaction()
            .add(R.id.container, filmsFragment, TAG_FILM)
            .add(R.id.container, watchlistFragment, TAG_WATCHLIST)
            .add(R.id.container, trendingFilmsFragment, TAG_TRENDING)
            .add(R.id.container,searchFilmsFragment, TAG_SEARCH)

            .hide(watchlistFragment)
            .hide(trendingFilmsFragment)
            .hide(searchFilmsFragment)
            .commit()
    }

    private fun showMainFragment(fragment: Fragment, isWatchlistFragment: Boolean) {

        if (isDetailViewAvailable()) {
            showPlaceholder()
        }

        if (isWatchlistFragment) {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .detach(fragment)
                .attach(fragment)
                .show(fragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit()
        }

        activeFragment = fragment
    }
    //Is Tablet??
    override fun onClick(film: Film) {

        if (!isDetailViewAvailable()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", film.id)
            intent.putExtra("filmType",activeFragment.tag.toString())
            startActivity(intent)
            detailViewIsSelected = false

        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_detail,
                    DetailFragment.newInstance(film.id, activeFragment.tag.toString())
                )
                .commit()

            detailViewIsSelected = true
        }
    }

    private fun isDetailViewAvailable() =
        findViewById<FrameLayout>(R.id.container_detail) != null

}