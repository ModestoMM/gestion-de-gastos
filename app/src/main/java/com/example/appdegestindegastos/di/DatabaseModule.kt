package com.example.appdegestindegastos.di

import android.content.Context
import androidx.room.Room
import com.example.appdegestindegastos.data.room.TransactionBd
import com.example.appdegestindegastos.data.room.TransactionDao
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
    fun provideTransactionDatabase(@ApplicationContext context: Context): TransactionBd {
        return Room.databaseBuilder(
            context,
            TransactionBd::class.java,
            "transaction_db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(database: TransactionBd): TransactionDao {
        return database.transactionDao()
    }
}
