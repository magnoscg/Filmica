package com.hispacode.filmica

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id")
        val film = FilmsRepo.findFilmById(id)

        film?.let {
            labelTitle.text = it.id
            labelGenres.text = it.genre
            labelDescription.text = it.overView
            labelDate.text = it.date
        }

        buttonAdd.setOnClickListener {
            Toast.makeText(this@DetailActivity,"Added to WatchList", Toast.LENGTH_LONG).show()

        }
    }
}
