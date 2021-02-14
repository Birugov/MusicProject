package ru.techcrat.musicproject.ui.main.adapters

import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_elem.view.*
import ru.techcrat.musicproject.model.Song
import ru.techcrat.musicproject.R

class RecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items:List<Song> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return LocalViewHolder(LayoutInflater.from(parent.context).inflate(
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
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LocalViewHolder->holder.bind(items[position]  )
        }
    }

    inner class LocalViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val artistName=itemView.artist_name_tv
        val songName=itemView.song_name_tv
        val songImg=itemView.song_iv


        fun bind(song: Song){
            val image:ByteArray?=getSongArt(song.songImg)
            if (image != null) {
                Glide.with(songImg.context)
                    .asBitmap()
                    .load(image)
                    .into(songImg)
            }
            artistName.text = song.artistName
            songName.text=song.songName

        }
    }

    fun getSongArt(uri:String):ByteArray?{
        val retriever= MediaMetadataRetriever()

        retriever.setDataSource(uri)
        val art:ByteArray? =retriever.embeddedPicture
        retriever.release()
        return art
    }

}
