package ru.techcrat.musicproject.ui.main.adapters

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_elem.view.*
import ru.techcrat.musicproject.Models.Song
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.ui.main.MainActivity

class RecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var  context: Context
    private var items:List<Song> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return LocalViewHolder(LayoutInflater.from(context).inflate(
        R.layout.recycler_elem,
        parent,
        false
    ))

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitData(song: List<Song>){
        items=song
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LocalViewHolder->holder.bind(items[position], context  )
        }
    }

    inner class LocalViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val artistName=itemView.artist_name_tv
        val songName=itemView.song_name_tv
        val songImg=itemView.song_iv


        fun bind(song: Song, context: Context){
            val image:ByteArray=getSongArt(song.songImg)
            Glide.with(context)
                .asBitmap()
                .load(image)
                .into(songImg)
            artistName.text = song.artistName
            songName.text=song.songName

        }
    }

    fun getSongArt(uri:String):ByteArray{
        val retriever= MediaMetadataRetriever()
        retriever.setDataSource(uri)
        val art:ByteArray=retriever.embeddedPicture
        retriever.release()
        return art
    }

}