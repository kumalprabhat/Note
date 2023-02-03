package com.prac.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdt :EditText
    lateinit var noteEdtDescription: EditText
    lateinit var addUpdateButton: Button
    lateinit var viewModel: NotesViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        supportActionBar?.hide()

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteEdtDescription = findViewById(R.id.idEDTNotDescription)
        addUpdateButton = findViewById(R.id.idButtonAddUpdate)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NotesViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteID", -1 )
            addUpdateButton.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdtDescription.setText(noteDescription)
        }else{
            addUpdateButton.setText("Save Note")
        }

        addUpdateButton.setOnClickListener{
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdtDescription.text.toString()
            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH: mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH: mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}