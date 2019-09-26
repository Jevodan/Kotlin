package com.example.jevodan.union.ui

import com.example.jevodan.union.data.model.Notes

infix fun Notes.concatt(note: Notes): Notes = this.copy(title = this.title + note.title)