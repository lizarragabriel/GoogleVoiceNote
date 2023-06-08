package com.lizarragabriel.mynote.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lizarragabriel.mynote.databinding.NoteItemBinding
import com.lizarragabriel.mynote.room.entity.Note
import com.lizarragabriel.mynote.utils.MyDiffUtil

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    private var mList: List<Note> = emptyList()

    var onItemClick: (Note) -> Unit = {}
    var onItemClick2: (Note) -> Unit = {}

    fun setList(newList: List<Note>) {
        val diffUtil = MyDiffUtil(mList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        mList = newList
        diffResults.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.myCheck.setOnClickListener {
                onItemClick(note)
            }
            itemView.setOnClickListener {
                onItemClick2(note)
            }
        }
    }

    fun getNumber(position: Int): Note {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}