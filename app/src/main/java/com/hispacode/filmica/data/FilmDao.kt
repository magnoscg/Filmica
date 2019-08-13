package com.hispacode.filmica.data

import android.arch.persistence.room.*

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film)

    @Query("SELECT * FROM film")
    fun getFilms() : List<Film>

    @Delete
    fun deleteFilm(film: Film)
}