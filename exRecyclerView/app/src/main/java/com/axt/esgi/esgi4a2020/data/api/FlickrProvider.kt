package com.axt.esgi.esgi4a2020.data.api

import com.axt.esgi.esgi4a2020.BuildConfig
import com.axt.esgi.esgi4a2020.data.dto.PhotoDetailDTO
import com.axt.esgi.esgi4a2020.data.dto.PhotosResponseDTO
import com.axt.esgi.esgi4a2020.data.dto.mapper.PhotoDetailMapper
import com.axt.esgi.esgi4a2020.data.dto.mapper.PhotosResponseMapper
import com.axt.esgi.esgi4a2020.data.model.Photo
import com.axt.esgi.esgi4a2020.data.model.PhotoDetail
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val USER_ID = "48933662@N05"
private const val PARAM_API_KEY = "api_key"
private const val PARAM_FORMAT = "format"
private const val PARAM_NOJSON_CALLBACK = "nojsoncallback"

object FlickrProvider {
    private var service: FlickrService

    init {
        service = Retrofit.Builder().baseUrl(BuildConfig.FLICKR_API_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickrService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor {
                val request = it.request()
                val url = request.url
                val builder = url.newBuilder()
                    .addQueryParameter(PARAM_API_KEY, BuildConfig.FLICKR_API_KEY)
                    .addQueryParameter(PARAM_FORMAT, BuildConfig.FLICKR_API_FORMAT)
                    .addQueryParameter(PARAM_NOJSON_CALLBACK, BuildConfig.FLICKR_API_CALLBACK)

                val newUrl = builder.build()
                val newRequest = request.newBuilder().url(newUrl).build()

                it.proceed(newRequest)
            }.build()
    }

    fun getPhotos(listener: Listener<List<Photo>>) {
        service.getPhotos(USER_ID).enqueue(object : Callback<PhotosResponseDTO> {
            override fun onFailure(call: Call<PhotosResponseDTO>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<PhotosResponseDTO>,
                response: Response<PhotosResponseDTO>
            ) {
                response.body()?.let { photosResponseDTO ->
                    val photos = PhotosResponseMapper().map(photosResponseDTO)
                    listener.onSuccess(photos)
                }
            }
        })
    }

    fun getPhotoDetail(photoId: String, listener: Listener<PhotoDetail>) {
        service.getPhotoInfo(photoId).enqueue(object : Callback<PhotoDetailDTO> {
            override fun onFailure(call: Call<PhotoDetailDTO>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<PhotoDetailDTO>,
                response: Response<PhotoDetailDTO>
            ) {
                response.body()?.let {
                    val photoDetail = PhotoDetailMapper().map(it)
                    listener.onSuccess(photoDetail)
                }
            }
        })
    }
}

interface Listener<T> {
    fun onSuccess(data: T)
    fun onError(t: Throwable)
}