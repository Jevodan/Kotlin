package com.example.jevodan.union.data.provider

import androidx.lifecycle.LiveData
import com.example.jevodan.union.data.entity.User
import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.data.model.NoteResult

interface RemoteDataProvider {

    /**
     * Срабатывает,когда изменяется коллекция записей (Notes)
     */
    fun subscribeToNotes(): LiveData<NoteResult>

    /**
     * Получение конкретной записи
     */
    fun getNoteById(id: String): LiveData<NoteResult>

    /**
     * Срабатывает,когда создаем новую запись
     */
    fun saveNote(note: Notes): LiveData<NoteResult>

    /**
     * удаление конкретной записи
     */
    fun removeNoteById(id: String): LiveData<NoteResult>

    fun getCurrentUser() : LiveData<User?>

}