package com.axt.esgi.esgi4a2020.data.model

data class PhotoDetail(
    val id: String,
    val url: String,
    val title: String,
    val description: String,
    val ownerUsername: String,
    val ownerName: String,
    val tags: List<String>
)