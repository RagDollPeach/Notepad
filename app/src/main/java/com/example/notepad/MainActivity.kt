package com.example.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.view.fragments.NotesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NotesFragment.newInstance())
                .commitNow()
        }
    }
}