package com.hispacode.filmica.view.films

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.util.BaseFilmAdapter
import com.hispacode.filmica.util.BaseFilmHolder
import com.hispacode.filmica.util.SimpleTarget
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_film.view.*


class FilmsAdapter(val listener: (Film) -> Unit) :
        BaseFilmAdapter<FilmsAdapter.FilmViewHolder>(
            layoutItem = R.layout.item_film,
            holderCreator = {view -> FilmViewHolder(view,listener)}
        ) {

    class FilmViewHolder(itemView: View, listener: (Film) -> Unit) :
        BaseFilmHolder(itemView, listener) {

        override fun bindFilm(film: Film) {
            super.bindFilm(film)

            with(itemView) {
                labelTitle.text = film.title
                labelGenre.text = film.genre
                labelRating.text = film.rating.toString()
                loadImage(film)
            }
        }

        private fun loadImage(it: Film) {
            val target = SimpleTarget { bitmap: Bitmap ->

                itemView.imgPoster.setImageBitmap(bitmap)
                setColor(bitmap)
            }
            //Avoid clearing memory saving the instance of Target
            itemView.imgPoster.tag = target

            Picasso.with(itemView.context)
                .load(it.getPosterUrl())
                .error(R.drawable.placeholder)
                .into(target)
        }

        private fun setColor(bitmap: Bitmap) {
            Palette.from(bitmap).generate {
                val defaultColor =
                    ContextCompat.getColor(itemView.context, R.color.colorPrimary)
                val swatch = it?.vibrantSwatch ?: it?.dominantSwatch
                val color = swatch?.rgb ?: defaultColor

                itemView.container.setBackgroundColor(color)
                itemView.container_data.setBackgroundColor(color)
            }
        }
    }
}