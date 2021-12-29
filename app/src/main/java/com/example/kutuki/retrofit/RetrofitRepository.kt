package com.example.kutuki.retrofit

import com.example.kutuki.Model.firstResponse
import com.example.kutuki.Model.videoResponseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val apiRequests: ApiRequests) {


    fun getCategory() : Observable<firstResponse> {
       return apiRequests.getCategory()
    }

    fun getVideos() : Observable<videoResponseModel>{
        return apiRequests.getVideos()
    }

}
