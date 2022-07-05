package com.example.musicplayer.feature_music.domain.usecase.scan_album

import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScanAlbumUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    operator fun invoke(): Flow<Resource<List<AlbumData>>> {
        return musicRepository.getAlbums()
    }

}