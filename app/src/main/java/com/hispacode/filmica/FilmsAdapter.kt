package com.hispacode.filmica

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FilmsAdapter(val listener: (Film) -> Unit) :
        RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    private val films = mutableListOf<Film>()


    override fun onCreateViewHolder(recyclerView: ViewGroup, type: Int): FilmViewHolder {
       val view = LayoutInflater.from(recyclerView.context).inflate(R.layout.item_film,
           recyclerView,
           false)

       return FilmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.film = film
    }
    //Update RecyclerView Adapter Films
    fun setFilms(list: MutableList<Film>) {
    this.films.clear()
        this.films.addAll(list)
        notifyDataSetChanged()
    }

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var film: Film? = null
        set(value) {
            field = value
            (itemView as TextView).text = value?.title
        }

        init {
            itemView.setOnClickListener {
                film?.let{
                    listener.invoke(it)
                }

            }
        }
    }

}