package com.example.pizzacodes.database

import androidx.lifecycle.LiveData

class CodeRepository(private val codeDao: CodeDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: LiveData<List<Code>> = codeDao.getAllCodes()

}