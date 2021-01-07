package com.example.note_maker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    private lateinit var tView2: EditText
    private lateinit var rView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tView2= findViewById(R.id.textView2)
        rView= findViewById(R.id.recyclerView)

        rView.layoutManager= LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        rView.adapter=adapter

        viewModel=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list ->
           list?.let{
            adapter.updateList(it)
           }
        })



    }

    override fun onItemClicked(note: Note) {
    viewModel.deleteNote(note)
        Toast.makeText(this,"item deleted",Toast.LENGTH_SHORT).show()
    }

    fun submitData(view: View) {
        val noteText= tView2.text.toString()
        if(noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
                Toast.makeText(this,"item inserted",Toast.LENGTH_SHORT).show()

            tView2.setText("")
        }
    }
}