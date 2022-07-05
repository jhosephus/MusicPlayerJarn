package com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list

import android.content.Context
import android.graphics.BitmapFactory
import android.icu.text.DecimalFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemMusictrackBinding
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception

class MusicTrackViewHolder(
    itemView: View,
    private val context: Context,
    private val musicTrackListener: MusicTrackListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemMusictrackBinding.bind(itemView)

    fun render(position : Int, musicTrack: MusicTrack) {

        /*Picasso
            .get()
            .load("file://${musicTrack.artist}")
            .noPlaceholder()
            .fit()
            .error(R.drawable.album_deafult_art)
            .into(binding.ivTrackIcon, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                    try {
                        val retriever = MediaMetadataRetriever()
                        retriever.setDataSource(musicTrack.data)
                        val data = retriever.embeddedPicture
                        retriever.release()
                        data.let {
                            if (it != null) {
                                binding.ivTrackIcon.setImageBitmap(
                                    BitmapFactory.decodeByteArray(it, 0, it.size)
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("MusicTrackViewHolder", "SetDataSource failure")
                        e.printStackTrace()
                    }
                }

            })*/
        Log.d("MusicTrackViewHolder", musicTrack.data)
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(musicTrack.data)
            val data = retriever.embeddedPicture
            retriever.release()
            if (data != null) {
                binding.ivTrackIcon.setImageBitmap(
                    BitmapFactory.decodeByteArray(data, 0, data.size)
                )
            } else {
                binding.ivTrackIcon.setImageDrawable(context.resources.getDrawable(R.drawable.album_deafult_art))
            }
        } catch (e: Exception) {
            Log.d("MusicTrackViewHolder", "SetDataSource failure")
            binding.ivTrackIcon.setImageDrawable(context.resources.getDrawable(R.drawable.album_deafult_art))
            e.printStackTrace()
        }
        binding.tvTitle.text = musicTrack.title
        binding.tvSubtitle.text = "${millisToDuration(musicTrack.duration)}"
        binding.root.setOnClickListener {
            musicTrackListener.OnTrackClick(position, musicTrack)
        }
    }

    private fun millisToDuration(millis : Int) : String {
        val minutes = millis/60000
        val seconds = (millis - minutes*60000)/1000
        val formatter = DecimalFormat("00")
        return "$minutes:${formatter.format(seconds)}"
    }

}