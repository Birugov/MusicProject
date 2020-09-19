package ru.techcrat.musicproject.Models

import android.net.Uri
import java.time.Duration

class Song(
    val uri: Uri,
    val songName:String,
    val artistName:String,
    val album:String,
    val duration: Int,
    val songImg:String
)
