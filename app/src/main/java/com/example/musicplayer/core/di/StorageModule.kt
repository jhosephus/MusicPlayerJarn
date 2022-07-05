package com.example.musicplayer.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.musicplayer.feature_music.data.storage.external.MediaProviderExternal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideMediaProviderExternal(@ApplicationContext context: Context) : MediaProviderExternal {
        return MediaProviderExternal(context)
    }

}