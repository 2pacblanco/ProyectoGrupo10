package com.example.proyecto_todolist_grupo10.model

import java.io.Serializable
import java.text.DateFormat

data class Users(val email: String, val pass: String, val name: String, var UsersLists: ArrayList<Lists>, val phone: String):
    Serializable {}

data class Lists(var items: ArrayList<Item>, val name: String, val position: Int, var itemsComplete: ArrayList<Item>?): Serializable {}

data class Item(var name: String, var estado: Int, var prioridad: Int, var notas : String , var plazo: String, var check : Int): Serializable {}