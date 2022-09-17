package com.example.notepad.view.fragments

import android.content.Context
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNoteFragment()
    }

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

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

        val pref = requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE)
        val noteText = binding.noteInput
        val titleText = binding.titleInput
        titleText.setText(pref.getString("title", ""))
        noteText.setText(pref.getString("text", ""))

        binding.clearButton.setOnClickListener { noteText.setText("") }
        binding.fab.setOnClickListener { switchFragment(NoteListFragment.newInstance()) }

        val viewModel: NoteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]

        binding.saveButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val text = binding.noteInput.text.toString()
            val dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm"))
            val note = Note(title, text, dateTime)
            viewModel.insert(note)
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
        requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE).edit().clear().apply()
        _binding = null
    }

}