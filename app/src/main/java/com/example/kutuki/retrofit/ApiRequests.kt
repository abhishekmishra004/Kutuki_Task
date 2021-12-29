package com.example.kutuki.retrofit

import com.example.kutuki.Model.firstResponse
import com.example.kutuki.Model.videoResponseModel
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiRequests {

    @GET("5e2bebd23100007a00267e51")
    fun getCategory(): Observable<firstResponse>

    @GET("5e2beb5a3100006600267e4e")
    fun getVideos() : Observable<videoResponseModel>

}