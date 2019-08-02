package com.hispacode.filmica

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val button: Button = findViewById(R.id.Button_add)

        button.setOnClickListener {
            Toast.makeText(this@DetailActivity,"Added to WatchList", Toast.LENGTH_LONG).show()

        }
    }
}
