package com.hispacode.filmica.view.trending


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hispacode.filmica.R
import com.hispacode.filmica.data.FilmsRepo
import com.hispacode.filmica.util.GridOffsetDecoration
import com.hispacode.filmica.view.films.FilmsAdapter
import com.hispacode.filmica.view.films.FilmsFragment
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.layout_error.*
import java.lang.IllegalArgumentException

class TrendingFilmsFragment : Fragment() {

    private lateinit var listener: FilmsFragment.OnFilmClickListener

    private val list: RecyclerView by lazy {
        listFilms.addItemDecoration(GridOffsetDecoration())
        return@lazy listFilms
    }
    private val adapter = FilmsAdapter {
        listener.onClick(it)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilmsFragment.OnFilmClickListener) {
            listener = context
        } else {
            throw  IllegalArgumentException(
                "The acttached activity isn't implementing " +
                        FilmsFragment.OnFilmClickListener::class.java.canonicalName
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter
        buttonRetry.setOnClickListener {
            reload()
        }
    }

    override fun onResume() {
        super.onResume()

        reload()
    }

    private fun reload() {
        showProgress()

        FilmsRepo.trendingFilms(context!!,
            { films ->
                adapter.setFilms(films)
                showList()
            },
            { error ->
                showError()
            })
    }

    private fun showList() {
        filmsProgress.visibility = View.INVISIBLE
        layoutError.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    private fun showError() {
        filmsProgress.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
        layoutError.visibility = View.VISIBLE
    }

    private fun showProgress() {
        filmsProgress.visibility = View.VISIBLE
        layoutError.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }
}