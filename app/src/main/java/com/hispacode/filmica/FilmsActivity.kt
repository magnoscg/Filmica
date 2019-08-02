package com.hispacode.filmica

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class FilmsActivity : AppCompatActivity() {

    private val list: RecyclerView by lazy {
        val list= findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        return@lazy list
    }
    private val adapter = FilmsAdapter {
        launchFilmDetail(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)
        adapter.setFilms(FilmsRepo.films)
        list.adapter = adapter



    }

    fun launchFilmDetail(film: Film) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", film.id)
        startActivity(intent)
    }
}