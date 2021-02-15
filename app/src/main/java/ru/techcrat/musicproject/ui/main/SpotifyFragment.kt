package ru.techcrat.musicproject.ui.main

import Track
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_spotify.*
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.retrofit.SpotifyApiService
import ru.techcrat.musicproject.ui.main.adapters.SpotifyRecyclerAdapter
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SpotifyFragment : Fragment(), CoroutineScope by MainScope(), SpotifyRecyclerAdapter.OnItemClickListener {
    val vm: MainViewModule by viewModel()
    lateinit var adapterSong: SpotifyRecyclerAdapter
    private var spotiitems:MutableList<Track> = ArrayList()
    private val service: SpotifyApiService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_spotify, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        vm.tokenService.tokenLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setupSpotifyRecycler()
                setupForPlayer()
            }
        })
    }

    private fun setupSpotifyRecycler() {
        launch {

            val response = withContext(Dispatchers.IO) {
                service.getSongs()
            }
            response.body()?.items?.map { it.track }?.let { adapterSong.submitData(it) }
        }

    }

    private fun setupForPlayer(){
        launch {
            val response= withContext(Dispatchers.IO){
                service.getSongs()
            }
            response.body()?.items?.map { it.track }?.let { passData(it as MutableList<Track>)}

        }
    }

    private fun initRecycler() {
        spotify_rv.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapterSong = SpotifyRecyclerAdapter(this@SpotifyFragment)
            adapter = adapterSong

        }
    }
     private fun passData(songs: MutableList<Track>) {
        for (i in songs){
            spotiitems.add(i)
        }
         adapterSong.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {

        val clickedItem= spotiitems[position]
        val uri= clickedItem.uri
        SpotifyService.playingState {
                when (it) {
                    PlayingState.PLAYING -> SpotifyService.pause()

                    PlayingState.PAUSED -> SpotifyService.play(uri)

                }
        }
        adapterSong.notifyDataSetChanged()

    }


}
