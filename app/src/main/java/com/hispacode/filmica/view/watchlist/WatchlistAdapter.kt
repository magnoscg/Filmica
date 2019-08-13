package com.hispacode.filmica.view.watchlist

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.view.View
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.util.BaseFilmAdapter
import com.hispacode.filmica.util.BaseFilmHolder
import com.hispacode.filmica.util.SimpleTarget
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_watchlist.view.*



class WatchlistAdapter(val listener: (Film) -> Unit): BaseFilmAdapter<WatchlistAdapter.WatchListHolder>(
    R.layout.item_watchlist,
    {view -> WatchListHolder(view,listener)}
) {

    class WatchListHolder(itemView: View, listener: (Film) -> Unit) : BaseFilmHolder(itemView,listener) {
        override fun bindFilm(film: Film) {
            super.bindFilm(film)
            with(itemView){
                labelTitle.text = film.title
                labelVotes.text = film.rating.toString()
                labelOverview.text = film.overView

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

                val overlayColor = Color.argb(
                    (Color.alpha(color) * 0.5).toInt(),
                    Color.red(color),
                    Color.green(color),
                    Color.blue(color)
                )

                itemView.imgOverlay.setBackgroundColor(overlayColor)
            }
        }
    }
}