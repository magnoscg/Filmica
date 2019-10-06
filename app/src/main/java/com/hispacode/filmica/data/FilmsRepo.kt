package com.hispacode.filmica.data

import android.arch.persistence.room.Room
import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hispacode.filmica.view.films.TAG_FILM
import com.hispacode.filmica.view.films.TAG_SEARCH
import com.hispacode.filmica.view.films.TAG_TRENDING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object FilmsRepo {

    //Fix film List, add 1 list for each Fragment
    //private val films: MutableList<Film> = mutableListOf()
    private val discoverFilmsList: MutableList<Film> = mutableListOf()
    private val trendingFilmsList: MutableList<Film> = mutableListOf()
    private val watchlistFilmsList: MutableList<Film> = mutableListOf()
    private val searchFilmsList: MutableList<Film> = mutableListOf()

    @Volatile
    private var db: FilmDatabase? = null

    private fun getDbInstance(context: Context) : FilmDatabase {
            if(db==null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "filmica-db"
                ).build()
            }
        return db as FilmDatabase
    }

    fun saveFilm(context: Context, film: Film, callback: (Film) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {

            val async = async(Dispatchers.IO) {

                val db = getDbInstance(context)
                db.filmDao().insertFilm(film)
            }
            async.await()
            callback.invoke(film)
        }
    }

    fun getFilms(
        context: Context,
        callback: (List<Film>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {

            val async = async(Dispatchers.IO) {
                val db = getDbInstance(context)
                db.filmDao().getFilms()
            }
            val films: List<Film> = async.await()
            watchlistFilmsList.clear()
            watchlistFilmsList.addAll(films)
            callback.invoke(watchlistFilmsList)
        }
    }
    fun deleteFilm(context: Context,film: Film, callback: (Film) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val async = async(Dispatchers.IO) {
                val db = getDbInstance(context)
                db.filmDao().deleteFilm(film)
            }
            async.await()
            callback.invoke(film)
        }
    }

    fun findFilmById(id: String, type: String): Film? {

        when(type) {

            TAG_FILM -> return discoverFilmsList.find { return@find it.id == id }
            TAG_TRENDING -> return trendingFilmsList.find { return@find it.id == id }
            TAG_SEARCH -> return searchFilmsList.find { return@find it.id == id }
            else -> return watchlistFilmsList.find { return@find it.id == id }
        }
    }

    fun discoverFilms(
        context: Context,
        page: Int = 1,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {

        if (discoverFilmsList.isEmpty() || (page > 1)) {

            val url = ApiRoutes.discoverMoviesUrl(page = page)

            val request = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val films =
                        Film.parseFilms(response.getJSONArray("results"))
                    //discoverFilmsList.clear()
                    discoverFilmsList.addAll(films)

                    onResponse.invoke(discoverFilmsList)
                },
                { error ->
                    error.printStackTrace()
                    onError.invoke(error)
                })

            Volley.newRequestQueue(context)
                .add(request)
        } else {
            onResponse.invoke(discoverFilmsList)
        }
    }

    fun trendingFilms(
        context: Context,
        page: Int = 1,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {

        if (trendingFilmsList.isEmpty() || (page > 1)) {
            val url = ApiRoutes.trendingMoviesUrl(page = page)

            val request = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val films =
                        Film.parseFilms(response.getJSONArray("results"))
                    //trendingFilmsList.clear()
                    trendingFilmsList.addAll(films)

                    onResponse.invoke(trendingFilmsList)
                },
                { error ->
                    error.printStackTrace()
                    onError.invoke(error)
                })

            Volley.newRequestQueue(context)
                .add(request)

        } else {
            onResponse.invoke(trendingFilmsList)
        }
    }

    fun searchFilms(
        query: String,
        context: Context,
        onResponse: (List<Film>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {

        val url = ApiRoutes.searchMoviesUrl(query)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val films = Film.parseFilms(response.getJSONArray("results"))
                searchFilmsList.clear()
                searchFilmsList.addAll(films)

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