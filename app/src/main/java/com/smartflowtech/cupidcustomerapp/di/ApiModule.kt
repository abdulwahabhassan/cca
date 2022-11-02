package com.smartflowtech.cupidcustomerapp.di


import com.smartflowtech.cupidcustomerapp.BuildConfig
import com.smartflowtech.cupidcustomerapp.data.api.CupidApiService
import com.smartflowtech.cupidcustomerapp.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesCupidApiService(
        networkClient: NetworkClient
    ): CupidApiService {
        return networkClient.getApiService(
            if (BuildConfig.DEBUG)
                BuildConfig.CUPID_PROD_BASE_URL
            else
                BuildConfig.CUPID_PROD_BASE_URL
        )
    }

}
