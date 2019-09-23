package com.example.jevodan.union.ui.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jevodan.union.R
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes
import kotlinx.android.synthetic.main.note_fragment.*
import java.util.*
import androidx.navigation.fragment.navArgs as navArgs1

@Suppress("ImplicitThis")
class NoteFragment : Fragment() {

    companion object {
        fun newInstance() = NoteFragment()
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
    }

    private var note: Notes? = null
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        val args = arguments?.let { NoteFragmentArgs.fromBundle(it) }
        this.note = args?.note

        if (this.note != null) {
            titleEditText.setText(this.note!!.title)
            descEditText.setText(this.note!!.desc)
        }
    }

    override fun onPause() {
        note = note?.copy(
            title = titleEditText.text.toString(),
            desc = descEditText.text.toString(),
            lastChanged = Date()
        ) ?: createNewNote()
        if (note != null) viewModel.save(note!!)
        super.onPause()
    }

    private fun createNewNote() = Notes(
        UUID.randomUUID().toString(),
        Color.VIOLET,
        titleEditText.text.toString(),
        descEditText.text.toString()
    )
}
