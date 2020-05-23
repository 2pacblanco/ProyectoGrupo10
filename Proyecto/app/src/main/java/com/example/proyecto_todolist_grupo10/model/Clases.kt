package com.example.proyecto_todolist_grupo10.model

import java.io.Serializable
import java.text.DateFormat

data class Users(val email: String, val pass: String, val name: String, var UsersLists: ArrayList<Lists>):
    Serializable {}

data class Lists(var items: ArrayList<Item>, val name: String, val position: Int): Serializable {}

data class Item(var name: String, var estado: Int, var prioridad: Int, var notas : String , var plazo: String): Serializable {}