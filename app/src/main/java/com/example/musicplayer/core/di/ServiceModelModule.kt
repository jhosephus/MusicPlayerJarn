package com.example.musicplayer.core.di

import com.example.musicplayer.core.domain.audio_service.AudioServiceModel
import com.example.musicplayer.core.domain.service.AudioPlayerServiceModel
import com.example.musicplayer.feature_music.data.repository.MusicRepositoryImpl
import com.example.musicplayer.feature_music.data.storage.external.MediaProviderExternal
import com.example.musicplayer.feature_music.domain.repository.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModelModule {

    @Provides
    @Singleton
    fun provideAudioPlayerServiceModel() : AudioPlayerServiceModel {
        return AudioPlayerServiceModel()
    }

    @Provides
    @Singleton
    fun provideAudioServiceModel() : AudioServiceModel {
        return AudioServiceModel()
    }

}