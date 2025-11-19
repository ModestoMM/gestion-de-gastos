package com.example.appdegestindegastos.di

import com.example.appdegestindegastos.data.repository.TransactionRepositoryImpl
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the [TransactionRepositoryImpl] to the [TransactionRepository] interface.
     * The `@Binds` annotation is a more efficient way to declare a provider when the implementation
     * is a direct constructor injection of an interface. This promotes coding against interfaces,
     * a core tenet of SOLID principles, allowing for easier testing and maintenance by decoupling
     * the application's components from concrete implementations.
     * @param transactionRepositoryImpl The concrete implementation of the repository.
     * @return An instance of [TransactionRepository].
     */
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}
