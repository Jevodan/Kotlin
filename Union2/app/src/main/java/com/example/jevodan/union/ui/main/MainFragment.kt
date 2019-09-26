package com.example.jevodan.union

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.adapter.NotesAdapter
import com.example.jevodan.union.ui.base.BaseFragment
import com.example.jevodan.union.ui.main.MainViewState
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment<List<Notes>?, MainViewState>() {

    /**
     * Делегирование функции lazy создание экземпляра
     * В этом случае ViewModel создастся только, когда потребуется
     */
    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.main_fragment
    private lateinit var adapter: NotesAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = NotesAdapter {
            viewModel.removeNote(it)
          //  val action = MainFragmentDirections.createNote(it)
        //    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }
        mainRecycler.adapter = adapter

        val note = Notes(color = Color.VIOLET)

        fab.setOnClickListener {
            val action = MainFragmentDirections.createNote(note)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun renderData(data: List<Notes>?) {
        data?.let { adapter.notes = it }
    }

}
