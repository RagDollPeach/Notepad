package com.example.notepad.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNotesListBinding
import com.example.notepad.model.data.Note
import com.example.notepad.view.adapters.NotesAdapter
import com.example.notepad.view.interfaces.Deletable
import com.example.notepad.view.interfaces.OnItemClick
import com.example.notepad.view.viewmodel.AppState
import com.example.notepad.view.viewmodel.NoteListViewModel


class NoteListFragment private constructor() : Fragment(), OnItemClick, Deletable{

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val flag by lazy {
        requireActivity().getSharedPreferences("FLAG", Context.MODE_PRIVATE)
    }

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private val viewModel: NoteListViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NoteListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendRequest()
        viewModel.getLifeData().observe(viewLifecycleOwner) { renderData(it) }
        binding.fab.setOnClickListener { switchFragment(CreateNoteFragment.newInstance()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_notes_view_change -> {
                chooseNoteView()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun chooseNoteView() {
        val menu = PopupMenu(
            requireContext(), requireActivity()
                .findViewById(R.id.menu_notes_view_change)
        )
        menu.menu.add("One column")
        menu.menu.add("Two columns")
        menu.menu.add("Three columns")
        menu.setOnMenuItemClickListener { item ->
            when (item.title) {
                "One column" -> flag.edit().putString("flag", "leaner").apply()
                "Two columns" -> flag.edit().putString("flag", "stagger").apply()
                "Three columns" -> flag.edit().putString("flag", "grid").apply()
            }
            switchFragment(newInstance())
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun renderData(appState: AppState) {
        val recyclerView = binding.recyclerView
        when (appState) {
            is AppState.Error -> {}
            is AppState.Success -> {
                recyclerView.layoutManager = flag.getString("flag", "")
                    ?.let { changeItemView(it) }
                recyclerView.adapter = NotesAdapter(appState.noteData, this, this)
            }
        }
    }

    private fun changeItemView(flag: String): LayoutManager {
        return when (flag) {
            "leaner" -> LinearLayoutManager(context)
            "stagger" -> StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            "grid" -> StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            else -> LinearLayoutManager(context)
        }
    }

    override fun deleteOnLongClick(title: String) {
        viewModel.delete(title)
    }

    override fun onItemClick(note: Note) {
        val pref = requireActivity().getSharedPreferences("notes", Context.MODE_PRIVATE)
        pref.edit().putString("title", note.title).apply()
        pref.edit().putString("text", note.text).apply()
        pref.edit().putString("date", note.date).apply()
        pref.edit().putString("color", note.color).apply()

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