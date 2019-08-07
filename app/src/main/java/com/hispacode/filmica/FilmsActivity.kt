package com.hispacode.filmica

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout

class FilmsActivity : AppCompatActivity(), FilmsFragment.OnFilmClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films)

        if(savedInstanceState == null) {
            val fragment = FilmsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.container,fragment)
                    .commit()
        }
    }
    //Is Tablet??
    override fun onClick(film: Film) {
        if (!isDetailViewAvailable()) {

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", film.id)
            startActivity(intent)

        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_detail, DetailFragment.newInstance(film.id))
                .commit()
        }
    }

    private fun isDetailViewAvailable() =
        findViewById<FrameLayout>(R.id.container_detail) != null

}