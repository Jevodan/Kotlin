package com.example.jevodan.union.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jevodan.union.data.model.NoteResult
import com.example.jevodan.union.data.model.Notes
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLL = "notes"
    }

    /**
     * Декларируем an instance of FirebaseFirestore
     */
    private val db = FirebaseFirestore.getInstance()

    /**
     * создаем коллекцию (as NOSQL, MongoDB)
     */
    private val notesReference = db.collection(NOTES_COLL)


    /**
     * snapshot - некое текущее состояние коллекции
     * addSnapshotListener - слушатель снайпшота, т.е состояния коллекции, срабатывает при изменении коллекции
     */
    override fun subscribeToNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.addSnapshotListener { snapshot, e ->
            e?.let { result.value = NoteResult.Error(e) }
            snapshot?.let {
                val notes = mutableListOf<Notes>()
                // берем все документа коллекции (снайпшота) и кладем в наш массив notes
                for (doc: QueryDocumentSnapshot in snapshot) {
                    notes.add(doc.toObject(Notes::class.java))
                }
                result.value = NoteResult.Success(notes)
            }
        }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(id).get().addOnSuccessListener {
            result.value = NoteResult.Success(it.toObject(Notes::class.java))
        }.addOnFailureListener {
            result.value = NoteResult.Error(it)
        }
        return result
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

    override fun saveNote(note: Notes): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference
            .document(note.id)
            .set(note)
            .addOnSuccessListener {
                Timber.d { "Запись ${note.id} сохранена" }
                result.value = NoteResult.Success(note)
            }
            .addOnFailureListener {
                Timber.e { "Ошибка сохранения $note: ${it.message}" }
                result.value = NoteResult.Error(it)
            }
        return result
    }

}