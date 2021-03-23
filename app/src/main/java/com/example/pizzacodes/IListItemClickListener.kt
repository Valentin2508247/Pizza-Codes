package com.example.pizzacodes

import com.example.pizzacodes.database.Code

interface IListItemClickListener {
    fun onListItemClick(code: Code)
}