package com.example.musicplayer.feature_music.domain.usecase.scan_music

import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanMusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    operator fun invoke(): Flow<Resource<List<MusicTrack>>> {
        return musicRepository.getMusic()
    }

}