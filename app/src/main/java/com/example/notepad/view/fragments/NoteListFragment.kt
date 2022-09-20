package com.example.notepad.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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


class NoteListFragment : Fragment(), OnItemClick, Deletable {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val flag by lazy { requireActivity().getSharedPreferences("FLAG", Context.MODE_PRIVATE) }

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
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLifeData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendRequest()
        binding.fab.setOnClickListener { switchFragment(CreateNoteFragment.newInstance()) }

        binding.noteViewChange.setOnClickListener {
            val menu = PopupMenu(requireContext(),binding.noteViewChange)
            menu.menu.add("One row")
            menu.menu.add("Two rows")
            menu.menu.add("Three rows")
            menu.setOnMenuItemClickListener { item ->
                when(item.title) {
                    "One row" -> flag.edit().putString("flag", "leaner").apply()
                    "Two rows" -> flag.edit().putString("flag", "stagger").apply()
                    "Three rows" -> flag.edit().putString("flag", "grid").apply()
                }
                switchFragment(newInstance())
                return@setOnMenuItemClickListener true
            }
            menu.show()
        }
    }

    private fun renderData(appState: AppState) {
        val recyclerView = binding.recyclerView
        when (appState) {
            is AppState.Error -> { /*binding.root.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE*/
            }
            is AppState.Success -> {
                /*binding.root.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE*/

                recyclerView.layoutManager = flag.getString("flag", "")?.let { changeItemView(it) }
                recyclerView.adapter = NotesAdapter(appState.noteData, this, this)
            }
        }
    }

    private fun changeItemView(flag: String): LayoutManager {
        return when (flag) {
            "leaner" -> LinearLayoutManager(context)
            "stagger" -> StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            "grid" -> GridLayoutManager(context, 3)
            else -> LinearLayoutManager(context)
        }
    }

    override fun deleteOnLongClick(title: String) {
        viewModel.delete(title)
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