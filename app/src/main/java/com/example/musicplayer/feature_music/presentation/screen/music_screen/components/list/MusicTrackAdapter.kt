package com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.feature_music.domain.model.MusicTrack

class MusicTrackAdapter(
    private val music: List<MusicTrack>,
    private val musicTrackListener: MusicTrackListener,
    private val context: Context
) : RecyclerView.Adapter<MusicTrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicTrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MusicTrackViewHolder(
            layoutInflater.inflate(R.layout.item_musictrack, parent, false),
            context,
            musicTrackListener = musicTrackListener
        )
    }

    override fun onBindViewHolder(holder: MusicTrackViewHolder, position: Int) {
        val item = music[position]
        holder.render(position, item)
    }

    override fun getItemCount(): Int {
        return music.size
    }
}