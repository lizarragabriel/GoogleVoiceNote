package com.lizarragabriel.mynote.utils

class MyNote(val myNote: String) {

    var hour = 0
    var minute = 0
    var note = ""

    fun myConvertNote(): Boolean {
        return try {
            hour = getJustHour()
            minute = getJustMinutes()
            note = getJustNote()
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    private fun getJustNote(): String {
        val myArray = myNote.split(":")
        return myArray[0].substring(0, myArray[0].length - 8)
    }

    private fun getJustHour(): Int {
        val myArray = myNote.split(":")
        var stringHour = myArray[0].substring(myArray[0].length - 2, myArray[0].length)
        var newHour = 0
        newHour = if(stringHour.contains(" ")) stringHour[1].toString().toInt() else stringHour.toInt()
        if(!isMorning()) stringHour += 12
        return newHour
    }

    private fun getJustMinutes(): Int {
        val myArray = myNote.split(":")
        val minute = myArray[1].substring(0, 2).toInt()
        if(minute > 59 || minute < 0) throw Exception("ajua")
        return minute
    }

    private fun isMorning(): Boolean {
        return !(myNote.contains(" p.m") || myNote.contains(" de la tarde") || myNote.contains(" de la noche"))
    }
}