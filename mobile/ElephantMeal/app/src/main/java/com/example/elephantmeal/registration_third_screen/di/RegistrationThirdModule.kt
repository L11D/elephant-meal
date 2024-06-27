package com.example.elephantmeal.registration_third_screen.di

import com.example.elephantmeal.registration_third_screen.domain.use_case.IRegistrationThirdRepository
import com.example.elephantmeal.registration_third_screen.domain.use_case.RegistrationThirdUseCase
import com.example.elephantmeal.registration_third_screen.repositories.RegistrationThirdApiService
import com.example.elephantmeal.registration_third_screen.repositories.RegistrationThirdRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RegistrationThirdModule {
    @Provides
    fun provideRegistrationThirdApiService(retrofit: Retrofit): RegistrationThirdApiService {
        return retrofit.create(RegistrationThirdApiService::class.java)
    }

    @Provides
    fun provideRegistrationThirdRepository(
        apiService: RegistrationThirdApiService
    ): IRegistrationThirdRepository {
        return RegistrationThirdRepositoryImpl(apiService)
    }

    @Provides
    fun provideRegistrationThirdUseCase(repository: IRegistrationThirdRepository): RegistrationThirdUseCase {
        return RegistrationThirdUseCase(repository)
    }
}