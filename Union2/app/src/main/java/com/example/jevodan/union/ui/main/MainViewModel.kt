package com.example.jevodan.union

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.jevodan.union.data.Repository
import com.example.jevodan.union.data.model.NoteResult
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.base.BaseViewModel
import com.example.jevodan.union.ui.main.MainViewState

class MainViewModel : BaseViewModel<List<Notes>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> {
        it?.let {
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState(myNotes = it.data as? List<Notes>)
                is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
            }
        }
    }

    private val repositoryNotes = Repository.subscribeToNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }

    fun removeNote(note: Notes) {
        Repository.removeNoteById(note.id).observeForever(Observer<NoteResult> {
            if (it == null) return@Observer
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState()
                is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
            }

        })
    }

    override fun viewState(): LiveData<MainViewState> = viewStateLiveData

}
