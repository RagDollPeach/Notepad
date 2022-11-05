package com.example.notepad.view.fragments

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.R
import com.example.notepad.databinding.FragmentCreateNoteBinding
import com.example.notepad.model.alarmreceiver.AlarmReceiver
import com.example.notepad.model.data.Note
import com.example.notepad.view.viewmodel.NoteListViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreateNoteFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

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
    private val calendarDate: Calendar = Calendar.getInstance()
    private val calendarTime: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields()
        setNoteColor()
        pressColoredChip()

        binding.timeButton.setOnClickListener { timePickerDialog() }
        binding.dateButton.setOnClickListener { datePickerDialog() }

        val viewModel: NoteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]
        saveNote(viewModel)
    }

    private fun setNoteColor() {
        noteColor = pref.getString("color", "yellow").toString()

        when (noteColor) {
            "red" -> binding.noteInput.setBackgroundColor(
                resources.getColor(
                    R.color.red,
                    requireActivity().theme
                )
            )
            "yellow" -> binding.noteInput.setBackgroundColor(
                resources.getColor(
                    R.color.yellow,
                    requireActivity().theme
                )
            )
            "purple" -> binding.noteInput.setBackgroundColor(
                resources.getColor(
                    R.color.purple,
                    requireActivity().theme
                )
            )
        }
    }

    private fun setFields() {
        val noteText = binding.noteInput
        val noteTitle = binding.titleInput

        noteTitle.setText(pref.getString("title", ""))
        noteText.setText(pref.getString("text", ""))
    }

    private fun pressColoredChip() {
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
    }

//    private fun timePicker() {
//        val piker = MaterialTimePicker.Builder()
//            .setTimeFormat(TimeFormat.CLOCK_24H)
//            .setHour(12)
//            .setMinute(0)
//            .setTitleText("Testing")
//            .build()
//
//        piker.show(requireActivity().supportFragmentManager,"note")
//
//        piker.addOnPositiveButtonClickListener {
//            val time = "${piker.hour}:${piker.minute}"
//            binding.setTimeTextView.text = time
//            calendar[Calendar.HOUR] = piker.hour
//            calendar[Calendar.MINUTE] = piker.minute
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setAlarm() {
        val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireActivity(), 0, intent, FLAG_MUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,(calendarDate.timeInMillis + calendarTime.timeInMillis) ,pendingIntent)

        Toast.makeText(context, "Alarm set", Toast.LENGTH_LONG).show()
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val date = if (month + 1 < 10) {
            "$day.0${month + 1}.$year"
        } else {
            "$day.${month + 1}.$year"
        }
        binding.setDateTextView.text = date
        calendarDate[Calendar.YEAR] = year
        calendarDate[Calendar.MONTH] = month
        calendarDate[Calendar.DAY_OF_MONTH] = day
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = "$hourOfDay:$minute"
        binding.setTimeTextView.text = time
        calendarTime[Calendar.HOUR] = hourOfDay
        calendarTime[Calendar.MINUTE] = minute
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timePickerDialog() {
        TimePickerDialog(
            requireContext(), this,
            Calendar.getInstance()[Calendar.HOUR],
            Calendar.getInstance()[Calendar.MINUTE],
            true
        ).show()
    }

    private fun datePickerDialog() {
        DatePickerDialog(
            requireContext(), this,
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH],
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
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
            if (binding.setDateTextView.text != "" && binding.setTimeTextView.text != "") {
                setAlarm()
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