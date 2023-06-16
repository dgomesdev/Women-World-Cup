package com.dgomesdev.remote.services

import com.dgomesdev.remote.model.MatchRemote
import retrofit2.http.GET

interface MatchesServices {

    @GET("MatchesAPI.json")
    suspend fun getMatchesService(): List<MatchRemote>
}