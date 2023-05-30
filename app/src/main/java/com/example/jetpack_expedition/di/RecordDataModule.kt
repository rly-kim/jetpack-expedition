package com.example.jetpack_expedition.di

import android.content.Context
import android.os.Environment
import com.example.jetpack_expedition.data.record.RecordDatasource
import com.example.jetpack_expedition.data.record.RecordDatasourceImpl
import com.example.jetpack_expedition.domain.repository.record.RecordRepository
import com.example.jetpack_expedition.domain.repository.record.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideRecordDatasource(): RecordDatasource = RecordDatasourceImpl()

    @Provides
    fun provideRecordRepository(@ApplicationContext context: Context, recordDatasource: RecordDatasource): RecordRepository = RecordRepositoryImpl(context, recordDatasource)
}
