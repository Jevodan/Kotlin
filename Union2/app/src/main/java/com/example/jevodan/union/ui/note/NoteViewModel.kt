package com.example.jevodan.union.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.example.jevodan.union.data.Repository
import com.example.jevodan.union.data.model.Notes

class NoteViewModel : ViewModel() {

    private var newNote: Notes? = null

    fun save(note: Notes) {
        newNote = note
    }

    override fun onCleared() {
        when {
            newNote != null -> Repository.saveNote(newNote!!)
            else -> Log.d("фейл","333")
        }

    }
}
