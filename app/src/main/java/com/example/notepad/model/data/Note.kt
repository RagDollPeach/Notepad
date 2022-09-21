package com.example.notepad.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val title: String, val text: String, val date: String, var color: String) : Parcelable

