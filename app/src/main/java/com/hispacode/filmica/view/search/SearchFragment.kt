package com.hispacode.filmica.view.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.hispacode.filmica.R
import com.hispacode.filmica.data.Film
import com.hispacode.filmica.data.FilmsRepo
import com.hispacode.filmica.util.GridOffsetDecoration
import com.hispacode.filmica.view.films.FilmsAdapter
import com.hispacode.filmica.view.films.FilmsFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_error.*
import java.lang.IllegalArgumentException

class SearchFragment : Fragment() {

    lateinit var listener: FilmsFragment.OnFilmClickListener
    private var query: String = ""

    private val list: RecyclerView by lazy {
        listFilms.addItemDecoration(GridOffsetDecoration())
        return@lazy listFilms
    }
    private val adapter = FilmsAdapter {
        listener.onClick(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilmsFragment.OnFilmClickListener) {
            listener = context
        } else {
            throw  IllegalArgumentException("The acttached activity isn't implementing " +
                    FilmsFragment.OnFilmClickListener::class.java.canonicalName
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter

        buttonRetry.setOnClickListener {
            reload(query)
        }

    }

    override fun onResume() {
        super.onResume()

        reload(query)
        onSearchListener()



    }

    private fun reload(query: String) {

        if(query != "") {

            showProgress()

            FilmsRepo.searchFilms(query, context!!,
                { films ->
                    adapter.setFilms(films)
                    if (films.isNotEmpty()) {
                        showList()
                    } else {
                        showNoResults()
                    }
                },
                { error ->
                    showError()
                })
        } else {
            showDefaultView()
        }

    }

    private fun showList() {
        filmsProgress.visibility = View.INVISIBLE
        layoutNoResults.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    private fun showDefaultView() {
        filmsProgress.visibility = View.INVISIBLE
        layoutNoResults.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }

    private fun showNoResults() {
        filmsProgress.visibility = View.INVISIBLE
        layoutNoResults.visibility = View.VISIBLE
        list.visibility = View.INVISIBLE
    }

    private fun showError() {
        filmsProgress.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
        layoutError.visibility = View.VISIBLE
    }

    private fun showProgress() {
        filmsProgress.visibility = View.VISIBLE
        layoutNoResults.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }


private fun onSearchListener() {

    searchText.setOnEditorActionListener { v, actionId, event ->
        query = searchText.text.toString()
        reload(query)
        searchText.hideKeyboard()
        true
    }
}

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }



}
