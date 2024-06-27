package com.example.elephantmeal.menu_screen.di

import com.example.elephantmeal.menu_screen.domain.use_case.IMenuRepository
import com.example.elephantmeal.menu_screen.domain.use_case.MenuUseCase
import com.example.elephantmeal.menu_screen.repositories.MenuApiService
import com.example.elephantmeal.menu_screen.repositories.MenuRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MenuScreenModule {

    @Provides
    fun provideMenuApiService(retrofit: Retrofit): MenuApiService {
        return retrofit.create(MenuApiService::class.java)
    }

    @Provides
    fun provideMenuRepository(apiService: MenuApiService): IMenuRepository {
        return MenuRepositoryImpl(apiService)
    }

    @Provides
    fun provideMenuUseCase(repository: IMenuRepository): MenuUseCase {
        return MenuUseCase(repository)
    }
}