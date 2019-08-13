package com.hispacode.filmica.view.watchlist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.data.FilmsRepo
import kotlinx.android.synthetic.main.fragment_watchlist.*


class WatchlistFragment : Fragment() {

    val adapter:WatchlistAdapter = WatchlistAdapter {
        showDetail(it)
    }

    private fun showDetail(film: Film) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchlist.adapter = adapter
    }
    override fun onResume() {
        super.onResume()
        FilmsRepo.discoverFilms(context,)
    }
}
