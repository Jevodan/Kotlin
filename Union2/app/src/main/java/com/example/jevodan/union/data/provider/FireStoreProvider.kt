package com.example.jevodan.union.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jevodan.union.data.entity.User
import com.example.jevodan.union.data.errors.NoAuthException
import com.example.jevodan.union.data.model.NoteResult
import com.example.jevodan.union.data.model.Notes
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLL = "notes"
        private const val USERS_COLL = "users"
    }

    /**
     * Декларируем an instance of FirebaseFirestore
     */
    private val db by lazy { FirebaseFirestore.getInstance() }

    /**
     * создаем коллекцию (as NOSQL, MongoDB)
     */
    private val notesReference by lazy { db.collection(NOTES_COLL) }
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    /**
     * У FirebaseUser юзера имеется уникальный uid,
     * по нему и определяем запись юзерра,а потом и заметки
     */
    private fun getUserNotes() = currentUser?.let {
        db.collection(USERS_COLL).document(it.uid).collection(NOTES_COLL)
    } ?: throw NoAuthException()

    /**
     * snapshot - некое текущее состояние коллекции
     * addSnapshotListener - слушатель снайпшота, т.е состояния коллекции, срабатывает при изменении коллекции
     */
    override fun subscribeToNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotes().addSnapshotListener { snapshot, e ->
                value = e?.let { NoteResult.Error(it) }
                    ?: let {
                        snapshot?.let {
                            val notes = it.documents.map { it.toObject(Notes::class.java) }
                            NoteResult.Success(notes)
                        }
                    }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }


    }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotes().document(id).get().addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(snapshot.toObject(Notes::class.java))
                }.addOnFailureListener {

                    value = NoteResult.Error(it)
                }

            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun removeNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(id).delete().addOnSuccessListener {
            Timber.d { "Запись удалена" }
            result.value = NoteResult.Success("__")
        }.addOnFailureListener {
            Timber.d { "Запись не удалена" }
            result.value = NoteResult.Error(it)
        }
        return result
    }

    override fun saveNote(note: Notes): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotes()
                .document(note.id)
                .set(note)
                .addOnSuccessListener {
                    Timber.d { "Запись ${note.id} сохранена" }
                    value = NoteResult.Success(note)
                }
                .addOnFailureListener {
                    Timber.e { "Ошибка сохранения $note: ${it.message}" }
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

}