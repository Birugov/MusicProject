package ru.techcrat.musicproject.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import ru.techcrat.musicproject.Models.LocalStorageProvider
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.ui.main.adapters.RecyclerAdapter

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {
    lateinit var adapterSong:RecyclerAdapter
     var provider= context?.let { LocalStorageProvider(it) }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        addDataSet()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun addDataSet(){
        val data= provider?.getAllAudio()
        if (data != null) {
            adapterSong.submitData(data)
        }
    }

    private fun initRecycler(){
        local_rv.apply {
            layoutManager=LinearLayoutManager(this.context)
            adapterSong=RecyclerAdapter()
            adapter=adapterSong

        }
    }

}
