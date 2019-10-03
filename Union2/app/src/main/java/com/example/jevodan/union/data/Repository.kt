package com.example.jevodan.union.data

import androidx.lifecycle.LiveData
import com.example.jevodan.union.data.entity.User
import com.example.jevodan.union.data.model.NoteResult
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.data.provider.FireStoreProvider
import com.example.jevodan.union.data.provider.RemoteDataProvider

object Repository : RemoteDataProvider {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    override fun getCurrentUser(): LiveData<User?> = remoteDataProvider.getCurrentUser()

    override fun removeNoteById(id: String) = remoteDataProvider.removeNoteById(id)

    override fun subscribeToNotes() = remoteDataProvider.subscribeToNotes()

    override fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

    override fun saveNote(note: Notes) = remoteDataProvider.saveNote(note)

}