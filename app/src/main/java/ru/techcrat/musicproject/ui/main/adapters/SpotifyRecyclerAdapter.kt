package ru.techcrat.musicproject.ui.main.adapters

import SpotifySong
import Track
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_elem.view.*
import ru.techcrat.musicproject.R
import kotlin.collections.ArrayList


class SpotifyRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var spotiitems: List<SpotifySong> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpotifyViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_elem,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpotifyRecyclerAdapter.SpotifyViewHolder->(holder.bind(spotiitems[position]))
        }
    }

    override fun getItemCount(): Int {
        return spotiitems.size
    }

    fun submitData(song: List<SpotifySong>) {
        spotiitems = song
        notifyDataSetChanged()
    }

    inner class SpotifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistName = itemView.artist_name_tv
        val songName = itemView.song_name_tv
        //val songImg = itemView.song_iv

        fun bind(song: SpotifySong) {
            val tracks:MutableList<Track> = ArrayList()
            for (i in song.items){
                tracks.add(i.track)
            }
            for (i in tracks) {
                songName.text = i.name
            }
            val names: MutableList<String> = ArrayList()
            for (i in tracks) {
                for (a in i.artists)
                names.add(i.name)
            }
            names.joinToString(",")
            artistName.text = names.toString()


        }
    }
}