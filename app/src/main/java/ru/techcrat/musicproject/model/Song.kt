package ru.techcrat.musicproject.model

import android.net.Uri

class Song(
    val uri: Uri,
    val songName:String,
    val artistName:String,
    val album:String,
    val duration: Int,
    val songImg:String
)
