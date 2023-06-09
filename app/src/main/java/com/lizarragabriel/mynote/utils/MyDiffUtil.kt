package com.lizarragabriel.mynote.utils

import androidx.recyclerview.widget.DiffUtil
import com.lizarragabriel.mynote.room.entity.Note

class MyDiffUtil(
    private val oldList: List<Note>,
    private val newList: List<Note>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }
            oldList[oldItemPosition].time != newList[newItemPosition].time -> {
                false
            }
            oldList[oldItemPosition].date != newList[newItemPosition].date -> {
                false
            }
            oldList[oldItemPosition].isComplete != newList[newItemPosition].isComplete -> {
                false
            }
            else -> true

        }
    }
}