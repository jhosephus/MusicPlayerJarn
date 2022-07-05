package com.example.musicplayer.core.di

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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMusicRepository(externalProvider: MediaProviderExternal) : MusicRepository {
        return MusicRepositoryImpl(externalProvider)
    }

}