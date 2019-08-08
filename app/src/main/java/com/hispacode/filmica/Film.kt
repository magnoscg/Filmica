package com.hispacode.filmica

import org.json.JSONArray
import org.json.JSONObject

data class Film(

        val id: String = "",
        val title: String = "No title",
        val genre: String = "No genre",
        val rating: Float = 0.0f,
        val overView: String = "No Overview",
        val date: String = "1999-01-1"
) {
        companion object {
                private fun parseFilm(jsonFilm: JSONObject): Film {
                        with(jsonFilm) {
                                return Film(
                                        id = getInt("id").toString(),
                                        title = getString("title"),
                                        overView = getString("overview"),
                                        rating = getDouble("vote_average").toFloat(),
                                        date = getString("release_date"),
                                        genre = parseGenres(jsonFilm.getJSONArray("genre_ids"))
                                )
                        }
                }
                fun parseFilms(filmsArray: JSONArray): List<Film> {
                        val films = mutableListOf<Film>()
                        for (index in 0 until filmsArray.length()) {
                                val film = parseFilm(filmsArray.getJSONObject(index))
                                films.add(film)
                        }
                        return films
                }
                private fun parseGenres(genresArray: JSONArray): String {
                        val genres = mutableListOf<String>()

                        for(index in 0 until genresArray.length()) {
                                val genreId = genresArray.getInt(index)
                                val genre = ApiConstants.genres[genreId] ?: ""
                                genres.add(genre)
                        }
                        return genres.reduce { acc, genre ->  "$acc | $genre"}
                }
        }
}