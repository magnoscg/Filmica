package com.hispacode.filmica.view.watchlist


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.R
import com.hispacode.filmica.R.string.*
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.data.FilmsRepo
import com.hispacode.filmica.util.BaseFilmHolder
import com.hispacode.filmica.util.SwipeToDeleteCallback
import com.hispacode.filmica.view.films.FilmsFragment
import kotlinx.android.synthetic.main.fragment_watchlist.*
import java.lang.IllegalArgumentException



class WatchlistFragment : Fragment() {

    lateinit var listener: FilmsFragment.OnFilmClickListener

    val adapter: WatchlistAdapter = WatchlistAdapter {
        listener.onClick(it)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilmsFragment.OnFilmClickListener) {
            listener = context
        } else {
            throw  IllegalArgumentException("The acttached activity isn't implementing " +
                    FilmsFragment.OnFilmClickListener::class.java.canonicalName
            )
        }
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

            Snackbar.make(watchlist, remove_from_watchlist, Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    FilmsRepo.saveFilm(context!!, film) {
                        adapter.insertFilm(position, film)
                    }
                }
                .setActionTextColor(Color.GREEN)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        FilmsRepo.getFilms(context!!) {
            adapter.setFilms(it)
        }
    }
}
