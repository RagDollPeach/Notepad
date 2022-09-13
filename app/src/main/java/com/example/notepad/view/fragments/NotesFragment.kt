package com.example.notepad.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNotesBinding
import com.example.notepad.view.viewmodel.AppState
import com.example.notepad.view.viewmodel.NotesViewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel

    companion object {
        fun newInstance() = NotesFragment()
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
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        viewModel.getLifeData().observe(viewLifecycleOwner)
        { renderData(it) }
        binding.fab.setOnClickListener { viewModel.sentRequest() }
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Error -> {/* TODO */}
            AppState.Loading -> {/* TODO */}
            is AppState.Success -> {
                Toast.makeText(requireContext(),"Working ${appState.noteData.title}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}