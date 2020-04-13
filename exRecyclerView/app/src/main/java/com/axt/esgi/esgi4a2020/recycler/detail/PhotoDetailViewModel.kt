package com.axt.esgi.esgi4a2020.recycler.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axt.esgi.esgi4a2020.data.api.FlickrProvider
import com.axt.esgi.esgi4a2020.data.api.Listener
import com.axt.esgi.esgi4a2020.data.model.PhotoDetail

class PhotoDetailViewModel : ViewModel() {
    private val photoDetailMutableLiveData = MutableLiveData<PhotoDetail>()
    val photoLiveData: LiveData<PhotoDetail> = photoDetailMutableLiveData

    private val errorlMutableLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = errorlMutableLiveData

    fun getPhotoDetail(photoId: String) {
        FlickrProvider.getPhotoDetail(photoId, object : Listener<PhotoDetail> {
            override fun onSuccess(data: PhotoDetail) {
                photoDetailMutableLiveData.postValue(data)
            }

            override fun onError(t: Throwable) {
                errorlMutableLiveData.postValue(t)
            }

        })
    }
}