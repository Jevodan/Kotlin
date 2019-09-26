package com.example.jevodan.union.ui.note

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.jevodan.union.R
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.base.BaseFragment
import kotlinx.android.synthetic.main.note_fragment.*
import java.util.*
import androidx.navigation.fragment.navArgs as navArgs1

class NoteFragment : BaseFragment<Notes?, NoteViewState>() {

    override val layoutRes: Int = R.layout.note_fragment

    private var note: Notes? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onPause() {
        note =
            note?.copy(
                title = titleEditText.text.toString(),
                desc = descEditText.text.toString(),
                lastChanged = Date()
            )
                ?: Notes(
                    UUID.randomUUID().toString(),
                    Color.VIOLET,
                    titleEditText.text.toString(),
                    descEditText.text.toString()
                )
        note?.let { viewModel.save(note!!) }
        super.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments?.let { NoteFragmentArgs.fromBundle(it) }
        this.note = args?.note
        this.note?.let {
            viewModel.loadNote(it)
            titleEditText.setText(it!!.title)
            descEditText.setText(it!!.desc)
        }
    }

    override fun renderData(data: Notes?) {
        this.note = data
    }

}
