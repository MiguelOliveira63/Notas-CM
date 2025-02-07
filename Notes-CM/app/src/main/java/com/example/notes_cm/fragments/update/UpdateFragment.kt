package com.example.notes_cm.fragments.update

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_cm.R
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.findViewById<TextView>(R.id.updateNote).text = args.currentNote.note

        val updateButton = view.findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            updateNote()
        }

        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            deleteNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToListFromUpdate)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return view
    }

    private fun updateNote() {
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()
        val descriptionText = view?.findViewById<EditText>(R.id.updateDescription)?.text.toString()
        val dateText = view?.findViewById<EditText>(R.id.updateDate)?.text.toString()

        if (noteText.isEmpty() || descriptionText.isEmpty() || dateText.isEmpty()) {
            Toast.makeText(context, "Não pode ser uma nota vazia!", Toast.LENGTH_LONG).show()
        } else {
            val note = Note(args.currentNote.id, noteText, descriptionText, dateText)

            mNoteViewModel.updateNote(note)

            Toast.makeText(requireContext(), "Nota atualizada com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Nota apagada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar a Nota?")
        builder.create().show()
    }
}