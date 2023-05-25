package com.example.jetpack_expedition.di

import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FilePath

@Module
@InstallIn(ViewModelComponent::class)
object RecordDataModule {
    @Provides
    @FilePath
    @ViewModelScoped
    fun provideFilePath(): String = Environment.getExternalStorageDirectory().absolutePath
}
