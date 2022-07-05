package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.AlbumFragment
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.ArtistFragment

class MusicLibraryPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AlbumFragment()
            1 -> ArtistFragment()
            else -> AlbumFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Album"
            1 -> "Artist"
            else -> "Default"
        }
    }
}