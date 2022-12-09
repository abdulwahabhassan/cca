package com.smartflowtech.cupidcustomerapp.di

import android.content.Context
import com.smartflowtech.cupidcustomerapp.database.AppRoomDatabase
import com.smartflowtech.cupidcustomerapp.database.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context
    ): AppRoomDatabase {
        return AppRoomDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(appDatabase: AppRoomDatabase): TransactionsDao {
        return appDatabase.getTransactionsDao()
    }

}
