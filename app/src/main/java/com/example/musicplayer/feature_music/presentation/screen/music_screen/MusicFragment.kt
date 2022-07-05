package com.example.musicplayer.feature_music.presentation.screen.music_screen

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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.core.domain.audio_service.AudioServiceCommand
import com.example.musicplayer.core.domain.audio_service.AudioServiceModel
import com.example.musicplayer.core.domain.audio_service.AudioServiceState
import com.example.musicplayer.core.domain.service.AudioPlayerService
import com.example.musicplayer.core.domain.service.AudioPlayerServiceModel
import com.example.musicplayer.databinding.FragmentMusicBinding
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list.MusicTrackAdapter
import com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list.MusicTrackListener
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicEvent
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MusicFragment : Fragment() {

    private var _binding : FragmentMusicBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel : MusicViewModel by activityViewModels()
    private lateinit var navController: NavController

    private var selectedIndex : Int = 0

    @Inject
    lateinit var audioPlayerServiceModel: AudioPlayerServiceModel

    @Inject
    lateinit var audioServiceModel: AudioServiceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.simpleToolbar.inflateMenu(R.menu.menu_music)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.collapsingToolbar.setupWithNavController(
            binding.simpleToolbar,
            navController,
            appBarConfiguration
        )
        binding.collapsingToolbar.title = "Selected List"
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.font_light))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.font_light))
        Picasso
            .get()
            .load("file://${musicViewModel.currentAlbum.value?.albumArt ?: ""}")
            .noPlaceholder()
            .error(R.drawable.album_deafult_art)
            .into(binding.ivListArt, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView(musicViewModel.currentSelection.value ?: emptyList())
        audioServiceModel.state.observe(this, Observer {
            if (it == AudioServiceState.Ready) {
                audioServiceModel.command.postValue(
                    AudioServiceCommand.StartMedia(selectedIndex)
                )
            }
        })
    }

    private fun initRecyclerView(music : List<MusicTrack>) {
        binding.rvSimpleList.layoutManager = LinearLayoutManager(activity)
        binding.rvSimpleList.adapter = MusicTrackAdapter(music, object: MusicTrackListener {
            override fun OnTrackClick(position: Int, track: MusicTrack) {
                selectedIndex = position
                Log.d("AudioService", "UpdateMedia")
                audioServiceModel.media.postValue(music)
                audioServiceModel.command.postValue(
                    AudioServiceCommand.UpdateMedia(music)
                )
            }
        }, requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        //requireActivity().unbindService(mConnection)
    }
}