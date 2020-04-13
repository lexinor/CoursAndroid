package com.axt.esgi.esgi4a2020.data.dto

import com.google.gson.annotations.SerializedName

data class PhotosResponseDTO(@SerializedName("photos") val photos: PhotosDTO)

data class PhotosDTO(@SerializedName("photo") val photoList: List<PhotoDTO>)

data class PhotoDTO(
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: String,
    @SerializedName("farm") val farm: String,
    @SerializedName("title") val title: String
)