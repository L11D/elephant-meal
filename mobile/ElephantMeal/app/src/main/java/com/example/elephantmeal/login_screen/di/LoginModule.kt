package com.example.elephantmeal.login_screen.di

import com.example.elephantmeal.login_screen.domain.use_case.ILoginRepository
import com.example.elephantmeal.login_screen.domain.use_case.LoginUseCase
import com.example.elephantmeal.login_screen.repositories.LoginApiService
import com.example.elephantmeal.login_screen.repositories.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    fun provideLoginRepository(apiService: LoginApiService): ILoginRepository {
        return LoginRepositoryImpl(apiService)
    }
    @Provides
    fun provideLoginUseCase(repository: ILoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }
}