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

    /**
     * Provides a singleton instance of the Room database [TransactionBd].
     * The `@Singleton` annotation ensures that only one instance of the database is created
     * throughout the application's lifecycle, which is a crucial performance optimization.
     * The database is built using the application context, ensuring it's tied to the
     * application lifecycle and not a specific activity or fragment.
     * @param context The application context provided by Hilt.
     * @return A singleton instance of [TransactionBd].
     */
    @Provides
    @Singleton
    fun provideTransactionDatabase(@ApplicationContext context: Context): TransactionBd {
        return Room.databaseBuilder(
            context,
            TransactionBd::class.java,
            "transaction_db"
        ).build()
    }

    /**
     * Provides an instance of the [TransactionDao].
     * This provider depends on the [TransactionBd] instance and abstracts the DAO's creation,
     * allowing it to be easily injected wherever needed without manual instantiation.
     * @param database The singleton database instance.
     * @return An instance of [TransactionDao].
     */
    @Provides
    fun provideTransactionDao(database: TransactionBd): TransactionDao {
        return database.transactionDao()
    }
}
