package ru.techcrat.musicproject.model

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
import androidx.annotation.RequiresApi

class LocalStorageProvider {


    @RequiresApi(Build.VERSION_CODES.Q)
    fun getAllAudio(context: Context):MutableList<Song>
    {
        val audioList = mutableListOf<Song>()
        val uri:Uri= EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA
        )
        val resolver:ContentResolver=context.contentResolver
        val query=resolver.query(uri,
            projection,
            null,
            null,
            null)
        query?.use {
            cursor ->
            val idColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn= cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val albumColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val artistColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val imageColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()){
                val id=cursor.getLong(idColumn)
                val title=cursor.getString(titleColumn)
                val album=cursor.getString(albumColumn)
                val artist=cursor.getString(artistColumn)
                val duration=cursor.getInt(durationColumn)
                val imageSource=cursor.getString(imageColumn)

                val contentUri=ContentUris.withAppendedId(EXTERNAL_CONTENT_URI,id)
                audioList+=Song(contentUri,title,artist,album,duration,imageSource)
            }
        }
        return audioList
    }
}
