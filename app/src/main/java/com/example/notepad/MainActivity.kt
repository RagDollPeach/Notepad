package com.example.notepad

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.core.content.res.ResourcesCompat
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.view.fragments.NoteListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        supportActionBar?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.color.white,theme))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NoteListFragment.newInstance())
                .commitNow()
        }

        getSharedPreferences("FLAG", Context.MODE_PRIVATE)
    }
}