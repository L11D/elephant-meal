package com.example.elephantmeal.menu_screen.repositories

import com.example.elephantmeal.menu_screen.domain.models.UpdateProfile
import com.example.elephantmeal.menu_screen.domain.models.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface MenuApiService {
    @GET("api/v1/user/")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ) : UserProfile

    @POST("api/v1/user/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ) : Response<Unit>

    @PUT("api/v1/user/update")
    suspend fun updateProfile(
        @Body newProfile: UpdateProfile,
        @Header("Authorization") token: String
    )
}