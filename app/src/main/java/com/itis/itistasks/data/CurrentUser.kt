package com.itis.itistasks.data

object CurrentUser {

    private var id: Int = -1

    fun set(id : Int) {
        this.id = id
    }
    fun get() : Int {
        return id
    }

    fun isEmpty() = id == -1
}