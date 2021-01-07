package com.example.note_maker

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> =noteDao.getallnotes()

    suspend fun insert(note :Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note :Note) {
        noteDao.delete(note)
    }

}