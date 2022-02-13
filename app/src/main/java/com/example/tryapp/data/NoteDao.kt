package com.example.tryapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note:Note);

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun readAllData(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)
}