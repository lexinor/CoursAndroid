package com.axt.esgi.esgi4a2020.data.api

import com.axt.esgi.esgi4a2020.data.dto.PhotoDetailDTO
import com.axt.esgi.esgi4a2020.data.dto.PhotosResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("?method=flickr.people.getPublicPhotos")
    fun getPhotos(@Query("user_id") userId: String): Call<PhotosResponseDTO>

    @GET("?method=flickr.photos.getInfo")
    fun getPhotoInfo(@Query("photo_id") photoId: String): Call<PhotoDetailDTO>
}