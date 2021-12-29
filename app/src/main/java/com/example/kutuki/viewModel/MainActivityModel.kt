package com.example.kutuki.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kutuki.Model.firstResponse
import com.example.kutuki.Model.videoData
import com.example.kutuki.Model.videoList
import com.example.kutuki.Model.videoResponseModel
import com.example.kutuki.dataClasses.videoDataModel
import com.example.kutuki.dataClasses.videoViewData
import com.example.kutuki.retrofit.RetrofitRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainActivityModel @Inject constructor(val retrofitRepository: RetrofitRepository) :
    ViewModel() {

    var error = MutableLiveData<String>("")
    var successCode = MutableLiveData<Int>(0)
    var categoryResponse = MutableLiveData<firstResponse>(null)

    var errorVideo = MutableLiveData<String>("")
    var successCodeVideo = MutableLiveData<Int>(0)
    var VideoResponse = MutableLiveData<videoResponseModel>(null)

    object video{
        var videoData = MutableLiveData<videoViewData>(null)
    }

    fun getResponse() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(retrofitRepository.getCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response -> onSuccess(response) }, { t -> onFailure(t) })
        );
    }

    fun getVideos() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(retrofitRepository.getVideos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response -> onSuccessVideo(response) }, { t -> onFailureVideo(t) })
        );
    }

    private fun onFailureVideo(t: Throwable?) {
        t?.let {
            errorVideo.value = t.message
        } ?: run { errorVideo.value = "Error is null" }
    }

    private fun onSuccessVideo(response: videoResponseModel?) {
        response?.let {
            VideoResponse.value = response
            successCodeVideo.value = response.code
            sortData(response.response.videos)
            Log.d("response", "onSuccess: " + Gson().toJson(response))
        } ?: run { successCodeVideo.value = -200 }
    }

    private fun sortData(videos: videoList) {
        val value = HashMap<String, ArrayList<videoDataModel>?>()
        var split = videos.data1.categories.split(",").toMutableList()
        setData(split, value, videos.data1)
        split.clear()
        split = videos.data2.categories.split(",").toMutableList()
        setData(split, value, videos.data2)
        split.clear()
        split = videos.data3.categories.split(",").toMutableList()
        setData(split, value, videos.data3)
        split.clear()
        split = videos.data4.categories.split(",").toMutableList()
        setData(split, value, videos.data4)
        split.clear()
        split = videos.data5.categories.split(",").toMutableList()
        setData(split, value, videos.data5)
        split.clear()
        split = videos.data6.categories.split(",").toMutableList()
        setData(split, value, videos.data6)
        split.clear()
        split = videos.data7.categories.split(",").toMutableList()
        setData(split, value, videos.data7)
        split.clear()
        split = videos.data8.categories.split(",").toMutableList()
        setData(split, value, videos.data8)
        split.clear()
        split = videos.data9.categories.split(",").toMutableList()
        setData(split, value, videos.data9)
        split.clear()
        split = videos.data10.categories.split(",").toMutableList()
        setData(split, value, videos.data10)
        split.clear()
        split = videos.data11.categories.split(",").toMutableList()
        setData(split, value, videos.data11)
        split.clear()
        split = videos.data12.categories.split(",").toMutableList()
        setData(split, value, videos.data12)
        split.clear()
        split = videos.data13.categories.split(",").toMutableList()
        setData(split, value, videos.data13)
        split.clear()
        split = videos.data14.categories.split(",").toMutableList()
        setData(split, value, videos.data14)
        split.clear()
        split = videos.data15.categories.split(",").toMutableList()
        setData(split, value, videos.data15)
        split.clear()
        val data = videoViewData(value)
        video.videoData.value = data
    }

    fun setData(
        split: List<String>,
        value: HashMap<String, ArrayList<videoDataModel>?>,
        data: videoData
    ) {
        for (type in split) {
            if (value.containsKey(type)) {
                var temp = value.get(type)
                val tempVideoData =
                    videoDataModel(data.title, data.description, data.thumbnailURL, data.videoURL)
                if (null == temp) temp = ArrayList();
                temp.add(tempVideoData)
            } else {
                val temp = ArrayList<videoDataModel>();
                val tempVideoData =
                    videoDataModel(data.title, data.description, data.thumbnailURL, data.videoURL)
                temp.add(tempVideoData)
                value.put(type, temp)
            }
        }
    }

    private fun onFailure(t: Throwable?) {
        t?.let {
            error.value = t.message
        } ?: run { error.value = "Error is null" }
    }

    private fun onSuccess(response: firstResponse?) {
        response?.let {
            categoryResponse.value = response
            successCode.value = response.code
            Log.d("response", "onSuccess: " + Gson().toJson(response))
        } ?: run { successCode.value = -200 }
    }

}