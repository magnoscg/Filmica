package com.hispacode.filmica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.FilmsRepo.discoverFilms
import kotlinx.android.synthetic.main.fragments_films.*
import kotlinx.android.synthetic.main.layout_error.*
import java.lang.IllegalArgumentException

class FilmsFragment: Fragment() {

    private lateinit var listener: OnFilmClickListener

    private val list: RecyclerView by lazy {
        listFilms.addItemDecoration(GridOffsetDecoration())
        return@lazy listFilms
    }
    private val adapter = FilmsAdapter {
        listener.onClick(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFilmClickListener) {
            listener = context
        } else {
            throw  IllegalArgumentException("The acttached activity isn't implementing " +
                    OnFilmClickListener::class.java.canonicalName
            )
        }
    }

  /* Previous Function without Fragments
    fun launchFilmDetail(film: Film) {

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", film.id)
        startActivity(intent)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragments_films,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setFilms(FilmsRepo.films)
        list.adapter = adapter
        buttonRetry.setOnClickListener {
            reload()
        }
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        showProgress()

        discoverFilms(context!!,
            { films ->
                adapter.setFilms(films)
                showList()
            },
            { error ->
                showError()
            })
    }

    private fun showList() {
        filmsProgress.visibility = View.INVISIBLE
        layoutError.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    private fun showError() {
        filmsProgress.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
        layoutError.visibility = View.VISIBLE
    }

    private fun showProgress() {
        filmsProgress.visibility = View.VISIBLE
        layoutError.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }

    //Listener Interface
    interface OnFilmClickListener {
        fun onClick(film: Film)
    }
}