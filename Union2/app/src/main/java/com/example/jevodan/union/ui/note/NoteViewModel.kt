package com.example.jevodan.union.ui.note

import android.util.Log
import androidx.lifecycle.Observer
import com.example.jevodan.union.data.Repository
import com.example.jevodan.union.data.model.NoteResult
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Notes?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var newNote: Notes? = null

    fun save(note: Notes) {
        newNote = note
    }

    override fun onCleared() {
        when {
            newNote != null -> Repository.saveNote(newNote!!)
            else -> Log.d("фейл", "333")
        }
    }

    fun loadNote(note: Notes) {
        Repository.getNoteById(note.id).observeForever(Observer<NoteResult> {
            if (it == null) return@Observer
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = it.data as? Notes)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }

        })
    }

    fun removeNote(note: Notes) {
        Repository.getNoteById(note.id).observeForever(Observer<NoteResult> {
            if (it == null) return@Observer
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = it.data as? Notes)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }

        })
    }
}
