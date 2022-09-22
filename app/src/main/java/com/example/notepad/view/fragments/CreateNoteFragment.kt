package com.example.notepad.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.R
import com.example.notepad.databinding.FragmentCreateNoteBinding
import com.example.notepad.model.data.Note
import com.example.notepad.view.viewmodel.NoteListViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CreateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNoteFragment()
    }

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private var noteColor: String = "yellow"
    private val pref: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            "notes",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteText = binding.noteInput
        val titleText = binding.titleInput
        noteColor = pref.getString("color","yellow").toString()

        titleText.setText(pref.getString("title", ""))
        noteText.setText(pref.getString("text", ""))

        when(noteColor) {
            "red" -> binding.noteInput.setBackgroundColor(resources.getColor(R.color.red, requireActivity().theme))
            "yellow" -> binding.noteInput.setBackgroundColor(resources.getColor(R.color.yellow, requireActivity().theme))
            "purple" -> binding.noteInput.setBackgroundColor(resources.getColor(R.color.purple, requireActivity().theme))
        }

        binding.redChip.setOnClickListener {
            binding.noteInput.setBackgroundResource(R.color.red)
            noteColor = "red"
        }

        binding.yellowChip.setOnClickListener {
            binding.noteInput.setBackgroundResource(R.color.yellow)
            noteColor = "yellow"
        }

        binding.purpleChip.setOnClickListener {
            binding.noteInput.setBackgroundResource(R.color.purple)
            noteColor = "purple"
        }

        val viewModel: NoteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]
        saveNote(viewModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote(viewModel: NoteListViewModel) {
        binding.fab.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val text = binding.noteInput.text.toString()
            val dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm"))
            val note = Note(title, text, dateTime, noteColor)
            if (pref.getString("title", "") == binding.titleInput.text.toString()) {
                viewModel.update(note)
            } else {
                viewModel.insert(note)
            }
            switchFragment(NoteListFragment.newInstance())
        }
    }

    private fun switchFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().getSharedPreferences("notes", Context.MODE_PRIVATE).edit().clear().apply()
        _binding = null
    }
}