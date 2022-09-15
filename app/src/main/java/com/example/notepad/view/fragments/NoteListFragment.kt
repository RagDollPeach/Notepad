package com.example.notepad.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNotesBinding
import com.example.notepad.model.data.Note
import com.example.notepad.view.adapters.NotesAdapter
import com.example.notepad.view.interfaces.OnItemClick
import com.example.notepad.view.viewmodel.AppState
import com.example.notepad.view.viewmodel.NoteListViewModel

class NoteListFragment : Fragment() , OnItemClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = NoteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: NoteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]
        viewModel.getLifeData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendRequest()
        binding.fab.setOnClickListener { switchFragment(CreateNoteFragment()) }
    }

    private fun renderData(appState: AppState) {
        val recyclerView = binding.recyclerView
        when(appState) {
            is AppState.Error -> { /*binding.root.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE*/}
            AppState.Loading -> {/*binding.root.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE*/}
            is AppState.Success -> {
                /*binding.root.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE*/
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = NotesAdapter(appState.noteData, this)
            }
        }
    }

    override fun onItemClick(note: Note) {
        val pref = requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE)
        pref.edit().putString("title", note.title).apply()
        pref.edit().putString("text", note.text).apply()
        pref.edit().putString("date", note.date).apply()

        switchFragment(CreateNoteFragment.newInstance())
    }

    private fun switchFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}