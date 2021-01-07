package com.example.note_maker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRVAdapter(private val context:Context, private val listner: INotesRVAdapter): RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder> (){

    val allNotes =ArrayList<Note>()
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
       val textview = itemView.findViewById<TextView>(R.id.text)
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       val viewhloder= NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewhloder.deleteButton.setOnClickListener{
          listner.onItemClicked(allNotes[viewhloder.adapterPosition])
        }
        return viewhloder
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
     val currentNote = allNotes[position]
        holder.textview.text=currentNote.text
    }

    fun updateList(newList: List<Note>)
    {
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }
}
interface INotesRVAdapter{
    fun onItemClicked(note: Note)
}
