package com.example.musicplayer.feature_music.domain.usecase.scan_artist

import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanArtistUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): Flow<Resource<List<ArtistData>>> {
        return musicRepository.getArtists()
    }
}