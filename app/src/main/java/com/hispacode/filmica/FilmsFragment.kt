package com.hispacode.filmica

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragments_films.*

class FilmsFragment: Fragment() {

    private val list: RecyclerView by lazy {
        listFilms.layoutManager = LinearLayoutManager(context)
        return@lazy listFilms
    }
    private val adapter = FilmsAdapter {
        launchFilmDetail(it)
    }

    fun launchFilmDetail(film: Film) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", film.id)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragments_films,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setFilms(FilmsRepo.films)
        list.adapter = adapter
    }


}