package com.example.notepad.view.adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.databinding.NoteItemBinding
import com.example.notepad.model.data.Note
import com.example.notepad.view.interfaces.Deletable
import com.example.notepad.view.interfaces.OnItemClick

class NotesAdapter(private val dataList: List<Note>, private val callback: OnItemClick
, private val deleteNote: Deletable
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class NoteViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note, position: Int) {
            val binding = NoteItemBinding.bind(itemView)
            binding.noteTitle.text = note.title
            binding.noteDate.text = note.date

            binding.root.setOnClickListener {
                callback.onItemClick(note)
            }

            binding.root.setOnLongClickListener {
                val menu = PopupMenu(view.context, view)
                menu.menu.add("Delete")
                menu.setOnMenuItemClickListener { menuItem: MenuItem ->
                    if (menuItem.title == "Delete") {
                        deleteNote.deleteOnLongClick(note.title)
                        notifyItemRemoved(position)
                    }
                    true
                }
                menu.show()
                true
            }
        }
    }
}