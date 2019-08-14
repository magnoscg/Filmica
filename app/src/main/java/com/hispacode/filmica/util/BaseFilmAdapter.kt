package com.hispacode.filmica.util

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.data.Film

open class BaseFilmAdapter<VH : BaseFilmHolder>(
    @LayoutRes val layoutItem: Int,
    private val holderCreator:(View) -> VH
    ) : RecyclerView.Adapter<VH>() {

    protected val list: MutableList<Film> = mutableListOf()

    override fun onCreateViewHolder(recyclerView: ViewGroup, p1: Int): VH {
        val view = LayoutInflater.from(recyclerView.context).inflate(layoutItem,recyclerView,false)

        return holderCreator.invoke(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val film = list[position]
        viewHolder.bindFilm(film)
    }
    fun setFilms(films: List<Film>) {
        this.list.clear()
        this.list.addAll(films)
        notifyDataSetChanged()
    }

    fun deleteFilm(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
    }


}