package com.example.jevodan.union.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jevodan.union.data.model.Color
import com.example.jevodan.union.data.model.Notes

object SimpleRepository {

    private val notesLiveData = MutableLiveData<List<Notes>>()
 val hhh = MutableLiveData<String>()
    var notes: MutableList<Notes> = mutableListOf(
        Notes(
            color = Color.GREEN,
            desc = "Манускрипт не по умолчанию"
        ),
        Notes(color = Color.RED),
        Notes(color = Color.VIOLET),
        Notes(color = Color.WHITE),
        Notes(color = Color.YELLOW),
        Notes(color = Color.BLUE)
    )
        private set

    init {
        notesLiveData.value = notes
        hhh.value = "lol"
    }

    fun getNotes(): LiveData<List<Notes>> {
        return notesLiveData
    }

    fun saveNote(note: Notes) {
        addOrReplace(note)
        notesLiveData.value = notes
    }


    private fun addOrReplace(note: Notes) {
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes.set(i, note)
                return
            }
        }
        notes.add(note)
    }
}