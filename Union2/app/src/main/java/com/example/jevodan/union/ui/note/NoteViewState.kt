package com.example.jevodan.union.ui.note

import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.base.BaseViewState

class NoteViewState(note: Notes? = null, error: Throwable? = null) : BaseViewState<Notes?>(note, error) {
}