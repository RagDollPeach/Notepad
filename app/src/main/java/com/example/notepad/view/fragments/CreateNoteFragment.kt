package com.example.notepad.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notepad.databinding.FragmentCreateNoteBinding


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE)

        binding.titleInput.setText(pref.getString("title", ""))
        binding.noteInput.setText(pref.getString("text", ""))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().getSharedPreferences("test", Context.MODE_PRIVATE).edit().clear().apply()
        _binding = null
    }

}