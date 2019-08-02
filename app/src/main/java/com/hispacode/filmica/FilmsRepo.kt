package com.hispacode.filmica

object FilmsRepo {

    val films: MutableList<Film> = mutableListOf()
    get() {
        if(field.isEmpty())
            field.addAll(dummyFilms())
        return field
    }

    fun requestFilms(): MutableList<Film> {
        if ( films.isEmpty()) {
            films.addAll(dummyFilms())
        }
        return films
    }
    private fun dummyFilms(): MutableList<Film> {

        return (1..10).map { i:Int ->
            return@map Film(title = "Film ${i}",
                overView = "OverView ${i}",
                genre = "Genre ${i}",
                rating = i.toFloat(),
                date = "2019-05-${i}"
            )
        }.toMutableList()
    }
}