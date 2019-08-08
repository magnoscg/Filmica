package com.hispacode.filmica

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

object FilmsRepo {

    val films: MutableList<Film> = mutableListOf()

    fun findFilmById(id: String): Film? {
        return films.find {
            return@find it.id == id
        }
    }

    fun discoverFilms(
        context: Context,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        val url = ApiRoutes.discoverMoviesUrl()

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val films = Film.parseFilms(response.getJSONArray("results"))
                this.films.addAll(films)

                onResponse.invoke(films)
            },
            { error ->
                error.printStackTrace()
                onError.invoke(error)
            })

        Volley.newRequestQueue(context)
            .add(request)
    }

    private fun dummyFilms(): MutableList<Film> {

        return (1..10).map { i: Int ->
            return@map Film(
                    id = "id $i",
                    title = "Film $i",
                    overView = "OverView $i",
                    genre = "Genre $i",
                    rating = i.toFloat(),
                    date = "2019-05-$i"
            )
        }.toMutableList()
    }
}