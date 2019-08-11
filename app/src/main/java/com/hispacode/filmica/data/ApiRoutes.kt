package com.hispacode.filmica.data

import android.net.Uri
import com.hispacode.filmica.BuildConfig

object ApiRoutes {

    fun discoverMoviesUrl(language: String = "en-US", sort: String = "popularity.desc"): String {
        return getUriBuilder()
            .appendPath("discover")
            .appendPath("movie")
            .appendQueryParameter("language", language)
            .appendQueryParameter("sorty_by", sort)
            .appendQueryParameter("include_adult", "false")
            .build().toString()
    }

    private fun getUriBuilder(): Uri.Builder =
        Uri.Builder()
            .scheme("https")
            .authority("api.themoviedb.org")
            .appendPath("3")
            .appendQueryParameter("api_key", BuildConfig.MovieDbApiKey)
}