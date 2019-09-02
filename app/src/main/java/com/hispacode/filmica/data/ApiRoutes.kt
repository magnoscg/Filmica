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

    fun trendingMoviesUrl(mediaType: String = "movie", timeWindow: String = "week") : String {
        return getUriBuilder()
            .appendPath("trending")
            .appendPath(mediaType)
            .appendPath(timeWindow)
            .build().toString()
    }

    fun searchMoviesUrl(query: String = "", language: String = "en-US"): String {
        return getUriBuilder()
            .appendPath("search")
            .appendPath("movie")
            .appendQueryParameter("query", query)
            .appendQueryParameter("language",language)
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