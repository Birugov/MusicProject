package ru.techcrat.musicproject.ui.main.adapters

import SpotifySong
import Track
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_elem.view.*
import ru.techcrat.musicproject.R

import kotlin.collections.ArrayList


class SpotifyRecyclerAdapter(private val listener:OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var spotiitems: List<Track> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpotifyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_elem,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpotifyRecyclerAdapter.SpotifyViewHolder -> (holder.bind(spotiitems[position]))
        }
    }

    override fun getItemCount(): Int {
        return spotiitems.size
    }

    fun submitData(songs: List<Track>) {
        spotiitems = songs
        notifyDataSetChanged()
    }

    inner class SpotifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val artistName = itemView.artist_name_tv
        val songName = itemView.song_name_tv
        val songImg = itemView.song_iv

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(song: Track) {
            songName.text = song.name
            artistName.text = song.artists.joinToString { it.name }
            var trackUrl: String
            for (i in song.album.images) {
                if (i.height == 300) {
                    trackUrl = i.url
                    Picasso.get().load(trackUrl).into(songImg)
                }
            }

        }

        override fun onClick(v: View?) {
            val position:Int=adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}
