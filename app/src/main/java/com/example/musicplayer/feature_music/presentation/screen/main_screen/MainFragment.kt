package com.example.musicplayer.feature_music.presentation.screen.main_screen

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
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMainBinding
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pager.MusicLibraryPagerAdapter
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicEvent
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicViewModel

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel : MusicViewModel by activityViewModels()
    private lateinit var navController: NavController


    private lateinit var musicLibraryPagerAdapter: MusicLibraryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding.defaultToolbar.inflateMenu(R.menu.menu_main)
        binding.defaultToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mi_refresh -> {
                    musicViewModel.onEvent(MusicEvent.ScanMusic)
                    true
                }
                else -> true
            }
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.defaultToolbar
            .setupWithNavController(navController, appBarConfiguration)


        // ViewPager
        musicLibraryPagerAdapter = MusicLibraryPagerAdapter(childFragmentManager)
        binding.pager.adapter = musicLibraryPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }

    override fun onStart() {
        super.onStart()
    }
}