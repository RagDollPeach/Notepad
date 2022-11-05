package com.example.notepad

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.notepad.databinding.ActivityMainBinding
import com.example.notepad.view.fragments.NoteListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        supportActionBar?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.color.white, theme))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NoteListFragment.newInstance())
                .commitNow()
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Channel"
            val description = "channel for notes alarm manager"
            val channel = NotificationChannel("noteschannel", name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }
}