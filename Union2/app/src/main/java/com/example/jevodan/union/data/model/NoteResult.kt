package com.example.jevodan.union.data.model

sealed class NoteResult {

    data class Success<out T>(val  data: T) : NoteResult() //data только получать , поэтому  out
    data class Error(val  error: Throwable) : NoteResult()

}