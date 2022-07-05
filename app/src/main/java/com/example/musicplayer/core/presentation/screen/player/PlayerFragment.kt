package com.example.musicplayer.core.presentation.screen.player

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.SeekBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.musicplayer.R
import com.example.musicplayer.core.domain.audio_service.AudioServiceCommand
import com.example.musicplayer.core.domain.audio_service.AudioServiceModel
import com.example.musicplayer.core.domain.audio_service.AudioServiceState
import com.example.musicplayer.core.domain.service.AudioPlayerServiceModel
import com.example.musicplayer.core.domain.service.MusicPlayerState
import com.example.musicplayer.core.domain.service.PlayerCommand
import com.example.musicplayer.core.presentation.transformations.resize_screen.PlayerScreenTransformer
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding : FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel : MusicViewModel by activityViewModels()
    private lateinit var navController: NavController

    lateinit var musicPlayerState: MusicPlayerState
    var currentTrack: MusicTrack? = null

    @Inject
    lateinit var audioPlayerServiceModel: AudioPlayerServiceModel
    @Inject
    lateinit var audioServiceModel : AudioServiceModel

    lateinit var playerScreenTransformer: PlayerScreenTransformer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        playerScreenTransformer = PlayerScreenTransformer(this.binding)
        binding.tvTrackTitle.setOnClickListener {
            if (binding.playerBody.visibility == View.GONE){
                playerScreenTransformer.fullScreen()
            } else {
                playerScreenTransformer.minimalExpand()
            }
        }
        binding.sbPlayerProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //if (p2) audioPlayerServiceModel.currentCommand.postValue(PlayerCommand.Progress(p1))
                if (p2) {
                    audioServiceModel.command.postValue(AudioServiceCommand.Progress(p1))
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navController = Navigation.findNavController(view)
    }

    override fun onStart() {
        super.onStart()

        audioServiceModel.playingMediaIndex.observe(this, Observer { index ->
            Log.d("PlayerFragment", "Updated index: ${index.toString()}")
            if (audioServiceModel.media.value?.isNotEmpty() == true) {
                Log.d("PlayerFragment", "List not empty")
                currentTrack = audioServiceModel.media.value?.get(index)
                currentTrack.let { track ->
                    Log.d("PlayerFragment", "Current track not null")
                    //Set track title
                    binding.tvTrackTitle.text = track!!.title
                    //Set Duration in progress
                    binding.sbPlayerProgress.max = track?.duration ?: 5000
                    //Set album art for playing song
                    val art = musicViewModel.albumResults.value!!.first{ album ->
                        album.id == track?.albumId ?: 0
                    }.albumArt

                    Log.d("AlbumArt", art)
                    val uri = Uri.parse(art)

                    Picasso
                        .get()
                        .load("file://$art")
                        .noPlaceholder()
                        .fit()
                        .error(R.drawable.album_deafult_art)
                        .into(binding.ivAlbumArt, object : Callback {
                            override fun onSuccess() {

                            }

                            override fun onError(e: Exception?) {
                                e?.printStackTrace()
                            }

                        })
                }
            }
        })
        audioServiceModel.trackState.observe(this, Observer {
            binding.sbPlayerProgress.progress = it
        })
        audioServiceModel.state.observe(this, Observer {
            Log.d("PlayerFragment", "Updated state: ${it.toString()}")
            when (it) {
                AudioServiceState.Idle -> {}
                AudioServiceState.Paused -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    binding.tvTrackTitle.text = currentTrack?.title ?: ""
                    binding.ibPlayStop.background = ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.ic_play_black, null)
                    binding.playerHeader.visibility = View.VISIBLE
                }
                AudioServiceState.Playing -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    binding.tvTrackTitle.text = currentTrack?.title ?: ""
                    binding.ibPlayStop.background = ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.ic_pause_black, null)
                    binding.playerHeader.visibility = View.VISIBLE
                }
                AudioServiceState.Ready -> {}
                AudioServiceState.Stopped -> {
                    playerScreenTransformer.vanish()
                }
            }
        })
        binding.ibPlayStop.setOnClickListener {
            when (audioServiceModel.state.value ?: AudioServiceState.Idle) {
                AudioServiceState.Idle -> {}
                AudioServiceState.Paused -> {
                    audioServiceModel.command.postValue(AudioServiceCommand.Play)
                }
                AudioServiceState.Playing -> {
                    audioServiceModel.command.postValue(AudioServiceCommand.Pause)
                }
                AudioServiceState.Ready -> {}
                AudioServiceState.Stopped -> {}
            }
        }
        binding.ibNextTrack.setOnClickListener {
            audioServiceModel.command.postValue(AudioServiceCommand.Next)
        }
        binding.ibPreviousTrack.setOnClickListener {
            audioServiceModel.command.postValue(AudioServiceCommand.Previous)
        }
    }
}