package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist

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
import com.example.musicplayer.databinding.FragmentArtistBinding
import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.Artist
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.domain.usecase.filter_music.FilterCriteria
import com.example.musicplayer.feature_music.presentation.screen.main_screen.MainFragmentDirections
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumAdapter
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumListener
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.components.list.ArtistAdapter
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.components.list.ArtistListener
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicEvent
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicViewModel

class ArtistFragment : Fragment() {

    private var _binding : FragmentArtistBinding? = null
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
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()
        musicViewModel.artistResults.observe(requireActivity(), Observer {
            //Log.d("ArtistFragment", it.toString())
            initRecyclerView(it)
        })
        musicViewModel.onEvent(MusicEvent.ScanArtist)
    }

    private fun initRecyclerView(artistList : List<ArtistData>) {
        binding.rvArtistList.layoutManager = GridLayoutManager(activity, 2)
        binding.rvArtistList.adapter = ArtistAdapter(artistList, object: ArtistListener {
            override fun onArtistClick(artist: ArtistData) {
                musicViewModel.currentArtist.postValue(artist)
                musicViewModel.onEvent(MusicEvent.FilterMusic(FilterCriteria.ArtistFilter(artist)))
                val action = MainFragmentDirections.actionMainFragmentToMusicFragment()
                navController.navigate(action)
            }
        })
    }

}