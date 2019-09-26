package com.example.jevodan.union.ui.main

import com.example.jevodan.union.data.model.Notes
import com.example.jevodan.union.ui.base.BaseViewState

class MainViewState(val myNotes: List<Notes>? = null, error: Throwable? = null) : BaseViewState<List<Notes>?>(myNotes, error)