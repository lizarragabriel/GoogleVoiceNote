package com.lizarragabriel.mynote.di

import android.content.Context
import androidx.room.Room
import com.lizarragabriel.mynote.room.dao.NoteDao
import com.lizarragabriel.mynote.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyModule {
    private const val DB_NAME = "notes.db"

    @Singleton
    @Provides
    fun mRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun mDao(appData: AppDatabase): NoteDao {
        return appData.myDao()
    }
}