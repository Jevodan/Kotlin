package com.example.jevodan.union

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.adapter.NotesAdapter
import com.example.jevodan.union.ui.main.MainViewState
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = NotesAdapter{
            val action = MainFragmentDirections.createNote(it)
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }

        mainRecycler.adapter = adapter

        viewModel.viewState().observe(this, Observer<MainViewState> {
            it?.let { adapter.notes = it.myNotes }
        })
        for (i in 10 until 100 step 3) {
            Log.d("test", "Хеллоу" + i)
        }

        val note = Notes(color = Color.VIOLET)

        fab.setOnClickListener {
            val action = MainFragmentDirections.createNote(note)
            Navigation.findNavController(it).navigate(action)
        }

    }

}
