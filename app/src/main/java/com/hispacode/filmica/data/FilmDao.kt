package com.hispacode.filmica.data

import android.arch.persistence.room.*

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: Film)

    @Query("SELECT * FROM FILM")
    fun getFilms(): List<Film>

    @Delete
    fun deleteFilm(film: Film)

    @Query("SELECT * FROM film WHERE id = :idFilm")
    fun getFilmById(idFilm: String): Film
}

