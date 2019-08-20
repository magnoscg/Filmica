package com.hispacode.filmica.view.watchlist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.data.FilmsRepo
import com.hispacode.filmica.util.BaseFilmHolder
import com.hispacode.filmica.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_watchlist.*
import java.text.FieldPosition


class WatchlistFragment : Fragment() {

    val adapter: WatchlistAdapter = WatchlistAdapter {
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

        setupSwipeHandler()
        watchlist.adapter = adapter
    }

    private fun setupSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {
                val film = (holder as BaseFilmHolder).film
                val position = holder.adapterPosition
                deleteFilm(film, position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(watchlist)
    }
    private fun deleteFilm(film: Film, position: Int) {
        FilmsRepo.deleteFilm(context!!,film) {
            adapter.deleteFilm(position)
        }
    }

    override fun onResume() {
        super.onResume()
        FilmsRepo.getFilms(context!!) {
            adapter.setFilms(it)
        }
    }
}
