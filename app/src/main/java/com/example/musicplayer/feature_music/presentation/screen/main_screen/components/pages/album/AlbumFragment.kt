package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAlbumBinding
import com.example.musicplayer.databinding.FragmentMainBinding
import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.domain.usecase.filter_music.FilterCriteria
import com.example.musicplayer.feature_music.presentation.screen.main_screen.MainFragmentDirections
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumAdapter
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumListener
import com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list.MusicTrackAdapter
import com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list.MusicTrackListener
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicEvent
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicViewModel

class AlbumFragment : Fragment() {

    private var _binding : FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel : MusicViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()
        musicViewModel.albumResults.observe(requireActivity(), Observer { albumResults ->
            initRecyclerView(albumResults)
            val desc = albumResults.map { "${it.albumTitle} by ${it.artistId}" }
            //Log.d("AlbumFragment", desc.toString())
        })
        musicViewModel.onEvent(MusicEvent.ScanAlbums)
    }

    private fun initRecyclerView(albumList : List<AlbumData>) {
        binding.rvAlbumList.layoutManager = GridLayoutManager(activity, 2)
        binding.rvAlbumList.adapter = AlbumAdapter(albumList, object: AlbumListener {
            override fun onAlbumClick(album: AlbumData) {
                musicViewModel.currentAlbum.postValue(album)
                musicViewModel.onEvent(MusicEvent.FilterMusic(FilterCriteria.AlbumFilter(album)))
                val action = MainFragmentDirections.actionMainFragmentToMusicFragment()
                navController.navigate(action)
            }
        })
    }

}